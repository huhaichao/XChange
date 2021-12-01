import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.GateioWebSocketSubscriptionMessage;
import dto.response.GateioKlineResponse;
import dto.response.GateioOrderBookResponse;
import dto.response.GateioTradesResponse;
import dto.response.GateioWebSocketTransaction;
import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.service.netty.JsonNettyStreamingService;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import info.bitrich.xchangestream.service.netty.WebSocketClientCompressionAllowClientNoContextAndServerNoContextHandler;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketClientExtensionHandler;
import io.reactivex.Observable;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.KlineInterval;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Author: Max Gao (gaamox@tutanota.com) Created: 05-05-2021 */
public class GateioStreamingService extends JsonNettyStreamingService {
  private static final Logger LOG = LoggerFactory.getLogger(GateioStreamingService.class);
  private static final String SUBSCRIBE = "subscribe";
  private static final String UNSUBSCRIBE = "unsubscribe";
  private static final String CHANNEL_NAME_DELIMITER = "-";

  public static final String SPOT_ORDERBOOK_CHANNEL = "spot.order_book";
  public static final String SPOT_KLINE_CHANNEL = "spot.candlesticks";
  public static final String SPOT_TRADES_CHANNEL = "spot.trades";
  public static final String SPOT_TICKERS_CHANNEL = "spot.tickers";

  private static final int MAX_DEPTH_DEFAULT = 5;
  private static final int UPDATE_INTERVAL_DEFAULT = 100;

  private final String apiUri;
  private ProductSubscription productSubscription;
  private ExchangeSpecification exchangeSpecification;

  private final Map<String, Observable<JsonNode>> subscriptions = new ConcurrentHashMap<>();
  private final Map<String, String> channelSubscriptionMessages = new ConcurrentHashMap<>();

  public GateioStreamingService(String apiUri, ExchangeSpecification exchangeSpecification) {
    super(apiUri, Integer.MAX_VALUE);
    this.apiUri = apiUri;
    this.exchangeSpecification = exchangeSpecification;
  }

  public Observable<GateioWebSocketTransaction> getRawWebSocketTransactions(String channelName, Object... args) {
    final ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();
    final CurrencyPair currencyPair =
            (args.length > 0 && args[0] instanceof CurrencyPair) ? ((CurrencyPair) args[0]) : null;
    return subscribeChannel(channelName, args)
        .filter(msg -> !msg.path("result").has("status"))
        .map(
            msg -> {
              switch (channelName) {
                case SPOT_ORDERBOOK_CHANNEL:
                  return mapper.readValue(msg.toString(), GateioOrderBookResponse.class);
                case SPOT_TRADES_CHANNEL:
                  return mapper.readValue(msg.toString(), GateioTradesResponse.class);
                case SPOT_KLINE_CHANNEL:
                  return mapper.readValue(msg.toString(), GateioKlineResponse.class);
              }
              return mapper.readValue(msg.toString(), GateioWebSocketTransaction.class);
            })
        .filter(t -> currencyPair.equals(t.getCurrencyPair()));
  }

  public void subscribeMultipleCurrencyPairs(ProductSubscription... products) {
    this.productSubscription = products[0];
  }

  public ProductSubscription getProduct() {
    return this.productSubscription;
  }

  @Override
  protected String getChannelNameFromMessage(JsonNode message) {
    String channel = message.path("channel") != null ? message.path("channel").asText() : "";
    String currencyPairOrderBook =
        message.path("result").path("s") != null ? message.path("result").path("s").asText() : "";
    String currencyPairTradesTickers =
        message.path("result").path("currency_pair") != null
            ? message.path("result").path("currency_pair").asText()
            : "";
    String currencyPairKline =
            message.path("result").path("n") != null
                    ? message.path("result").path("n").asText()
                    : "";
    return new StringBuilder(channel)
        .append(CHANNEL_NAME_DELIMITER)
        .append(currencyPairOrderBook)
        .append(currencyPairTradesTickers)
         .append(currencyPairKline)
        .toString();
  }

  @Override
  public Observable<JsonNode> subscribeChannel(String channelName, Object... args) {
    final CurrencyPair currencyPair =
        (args.length > 0 && args[0] instanceof CurrencyPair) ? ((CurrencyPair) args[0]) : null;
    final String klineInterval =  (args.length > 1 && args[1] instanceof KlineInterval) ?
            ((KlineInterval) args[1]).getCodeSimple().concat("_") : "";
    String currencyPairChannelName =
        String.format("%s-%s%s", channelName, klineInterval,currencyPair.toString().replace('/', '_'));

    try {
      // Example channel name key: spot.order_book_update-ETH_USDT, spot.trades-BTC_USDT
      if (!channels.containsKey(currencyPairChannelName)
          && !subscriptions.containsKey(currencyPairChannelName)) {
        subscriptions.put(
                currencyPairChannelName, super.subscribeChannel(currencyPairChannelName, args));
        channelSubscriptionMessages.put(
                currencyPairChannelName, getSubscribeMessage(currencyPairChannelName, args));
      }
    } catch (IOException e) {
      LOG.error("Failed to subscribe to channel: {}", currencyPairChannelName);
    }

    return subscriptions.get(currencyPairChannelName);
  }

  /**
   * Returns a JSON String containing the subscription message.
   *
   * @param channelName
   * @param args CurrencyPair to subscribe to
   * @return
   * @throws IOException
   */
  @Override
  public String getSubscribeMessage(String channelName, Object... args) throws IOException {
    GateioWebSocketSubscriptionMessage subscribeMessage = null;
    final CurrencyPair currencyPair =
            (args.length > 0 && args[0] instanceof CurrencyPair) ? ((CurrencyPair) args[0]) : null;
    String channel = channelName.split(CHANNEL_NAME_DELIMITER)[0];
    switch (channel) {
        case SPOT_ORDERBOOK_CHANNEL:
        case SPOT_TRADES_CHANNEL:
          final int maxDepth =
                  exchangeSpecification.getExchangeSpecificParametersItem("maxDepth") != null
                          ? (int) exchangeSpecification.getExchangeSpecificParametersItem("maxDepth")
                          : MAX_DEPTH_DEFAULT;
          final int msgInterval =
                  exchangeSpecification.getExchangeSpecificParametersItem("updateInterval") != null
                          ? (int) exchangeSpecification.getExchangeSpecificParametersItem("updateInterval")
                          : UPDATE_INTERVAL_DEFAULT;
          subscribeMessage = new GateioWebSocketSubscriptionMessage(
                  channel,
                  SUBSCRIBE,
                  currencyPair,
                  msgInterval,
                  maxDepth);
          break;
        case SPOT_KLINE_CHANNEL:
          final KlineInterval klineInterval =
                  (args.length > 1 && args[1] instanceof KlineInterval) ? ((KlineInterval) args[1]) : null;
          subscribeMessage = new GateioWebSocketSubscriptionMessage(
                  channel,
                  SUBSCRIBE,
                  currencyPair,
                  klineInterval
                  );
          break;
    }
    return objectMapper.writeValueAsString(subscribeMessage);
  }

  @Override
  protected WebSocketClientExtensionHandler getWebSocketClientExtensionHandler() {
    return WebSocketClientCompressionAllowClientNoContextAndServerNoContextHandler.INSTANCE;
  }

  @Override
  public String getUnsubscribeMessage(String channelName, Object... args) throws IOException {
    GateioWebSocketSubscriptionMessage unsubscribeMessage =
        objectMapper.readValue(
            channelSubscriptionMessages.get(channelName), GateioWebSocketSubscriptionMessage.class);
    unsubscribeMessage.setEvent(UNSUBSCRIBE);
    return objectMapper.writeValueAsString(unsubscribeMessage);
  }
}
