package info.bitrich.xchangestream.huobi;

import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import info.bitrich.xchangestream.huobi.dto.HuobiKlineType;
import info.bitrich.xchangestream.huobi.dto.HuobiStepType;

import java.util.Date;
import java.util.List;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.KlineInterval;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HuobiManualExample {

  private static final Logger LOG = LoggerFactory.getLogger(HuobiManualExample.class);

  public static void main(String[] args) {
    StreamingExchange exchange =
        StreamingExchangeFactory.INSTANCE.createExchange(HuobiStreamingExchange.class);

    exchange.connect().blockingAwait();

   /* exchange
        .getStreamingMarketDataService()
        .getTicker(CurrencyPair.ETH_BTC)
        .subscribe(
            ticker -> {
              LOG.info("Ticker: {}", ticker);
            },
            throwable -> LOG.error("ERROR in getting ticker: ", throwable));

    exchange
        .getStreamingMarketDataService()
        .getTicker(CurrencyPair.ETH_BTC, HuobiKlineType.MIN5.getName())
        .subscribe(
            ticker -> {
              LOG.info("Ticker MIN5: {}", ticker);
            },
            throwable -> LOG.error("ERROR in getting ticker: ", throwable));

    exchange
        .getStreamingMarketDataService()
        .getTrades(CurrencyPair.ETH_BTC)
        .subscribe(
            trade -> {
              LOG.info("Trade: {}", trade);
            },
            throwable -> LOG.error("ERROR in getting trade: ", throwable));

    exchange
        .getStreamingMarketDataService()
        .getOrderBook(CurrencyPair.ETH_BTC, HuobiStepType.STEP0.getName())
        .subscribe(
            orderBook -> {
              List<LimitOrder> bids = orderBook.getBids();
              List<LimitOrder> asks = orderBook.getAsks();
              LOG.info("*************************************************");
              LOG.info("Asks");
              LOG.info("Order Book 3 Lower Ask: {}", asks.get(2).getLimitPrice());
              LOG.info("Order Book 2 Lower Ask: {}", asks.get(1).getLimitPrice());
              LOG.info("Order Book 1 Lower Ask: {}", asks.get(0).getLimitPrice());
              LOG.info("-------------------------------------------------");
              LOG.info("Order Book 1 Higher Bid: {}", bids.get(0).getLimitPrice());
              LOG.info("Order Book 2 Higher Bid: {}", bids.get(1).getLimitPrice());
              LOG.info("Order Book 3 Higher Bid: {}", bids.get(2).getLimitPrice());
              LOG.info("Bids");
              LOG.info("*************************************************");
            },
            throwable -> LOG.error("ERROR in getting order book: ", throwable));*/

    exchange.getStreamingMarketDataService()
            .getKlines(CurrencyPair.BTC_USDT, KlineInterval.h1,null)
            .subscribe(
               kline -> {
                   LOG.info("*************************************************");
                   LOG.info("k line open "+ DateUtils.fromUnixTime(kline.getId()));
                   LOG.info("k line open "+kline.getOpen());
                   LOG.info("k line close "+kline.getClose());
                   LOG.info("k line high "+kline.getHigh());
                   LOG.info("k line low "+kline.getLow());
                   LOG.info("k line amount "+kline.getAmount());
                   LOG.info("k line cunt "+kline.getCount());
                   LOG.info("*************************************************");
               }
            );

  }
}
