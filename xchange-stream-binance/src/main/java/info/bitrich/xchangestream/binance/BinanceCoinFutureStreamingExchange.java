package info.bitrich.xchangestream.binance;

import org.knowm.xchange.ExchangeType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Simple endpoint switch as we cannot inject it when setting up the endpoint. */
public class BinanceCoinFutureStreamingExchange extends BinanceStreamingExchange {
  private static final Logger LOG = LoggerFactory.getLogger(BinanceCoinFutureStreamingExchange.class);
  private static final String WS_API_BASE_URI = "wss://dstream.binance.com/";

  @Override
  public ExchangeType getExchangeType(){
    return ExchangeType.BINANCECOINFUTURESTREAM;
  }

  protected String getStreamingBaseUri() {
    return WS_API_BASE_URI;
  }
}
