package info.bitrich.xchangestream.okcoin;

import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.KlineInterval;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OkExKlineManualExample {

    private static final Logger LOG = LoggerFactory.getLogger(OkExKlineManualExample.class);

    public static void main(String[] args) throws InterruptedException {
        StreamingExchange exchange =
                StreamingExchangeFactory.INSTANCE.createExchange(OkExStreamingExchange.class);
        exchange.connect().blockingAwait();

        exchange
                .getStreamingMarketDataService()
                .getKlines(CurrencyPair.BTC_USDT, KlineInterval.m1)
                .subscribe(
                        kline -> {
                            LOG.info("kline: {}", kline);
                        },
                        throwable -> LOG.error("ERROR in getting kline: ", throwable));


        exchange
                .getStreamingMarketDataService()
                .getKlines(CurrencyPair.ETH_USDT, KlineInterval.m1)
                .subscribe(
                        kline -> {
                            LOG.info("kline: {}", kline);
                        },
                        throwable -> LOG.error("ERROR in getting kline: ", throwable));


        Thread.sleep(Integer.MAX_VALUE);

    }
}
