package info.bitrich.xchangestream.poloniex;

import info.bitrich.xchangestgream.poloniex.PoloniexStreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import io.reactivex.disposables.Disposable;
import org.knowm.xchange.currency.CurrencyPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PoloniexManualExample {
    private static final Logger LOG = LoggerFactory.getLogger(PoloniexManualExample.class);

    public static void main(String[] args) {
        StreamingExchange exchange = StreamingExchangeFactory.INSTANCE.createExchange(PoloniexStreamingExchange.class.getName());
        exchange.connect().blockingAwait();

//        exchange.getStreamingMarketDataService().getOrderBook(CurrencyPair.BTC_USD).subscribe(orderBook -> {
//            LOG.info("First ask: {}", orderBook.getAsks().get(0));
//            LOG.info("First bid: {}", orderBook.getBids().get(0));
//        }, throwable -> LOG.error("ERROR in getting order book: ", throwable));

        Disposable subscribe = exchange.getStreamingMarketDataService().getTicker(CurrencyPair.BTC_USD).subscribe(ticker -> {
            LOG.info("TICKER: {}", ticker);
        }, throwable -> LOG.error("ERROR in getting ticker: ", throwable));

//        exchange.getStreamingMarketDataService().getTrades(CurrencyPair.BTC_USD).subscribe(trade -> {
//            LOG.info("TRADE: {}", trade);
//        }, throwable -> LOG.error("ERROR in getting trades: ", throwable));

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        subscribe.dispose();

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
