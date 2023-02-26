package info.bitrich.xchangestream.binance;

import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import info.bitrich.xchangestream.dto.WrapCurrency;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.marketdata.KlineInterval;

public class BinanceFutureStreamKlineRealtimeExample {

  public static void main(String[] args) throws InterruptedException {
    final ExchangeSpecification exchangeSpecification =
        new ExchangeSpecification(BinanceCoinFutureStreamingExchange.class);
    exchangeSpecification.setShouldLoadRemoteMetaData(false);
    BinanceCoinFutureStreamingExchange exchange =(BinanceCoinFutureStreamingExchange)
        StreamingExchangeFactory.INSTANCE.createExchange(exchangeSpecification);
    ProductSubscription subscription = ProductSubscription.create()
            //.addKlines(new WrapCurrency(new CurrencyPair("ETH/USD_221230"), KlineInterval.m1,null))
            //.addKlines(new WrapCurrency(new CurrencyPair("ETH/USD_perp"), KlineInterval.m1,null))
            //.addTicker(new CurrencyPair("ETH/USD_221230"))
            .addTicker(new FuturesContract("ETH/USD/221230"))
            .build();
    exchange.connect(subscription).blockingAwait();
   /*exchange
        .getStreamingMarketDataService()
        .getTicker(new CurrencyPair("ETH/USD_221230)"))
        .subscribe(System.out::println);*/

     /*exchange
            .getStreamingMarketDataService()
            .getKlines(new CurrencyPair("ETH/USD_perp"),KlineInterval.m1, null)
            .subscribe(System.out::println);*/

    exchange
            .getStreamingMarketDataService()
            .getTicker(new FuturesContract("ETH/USD/221230"))
            .subscribe(System.out::println);
    exchange.enableLiveSubscription();

    Thread.sleep(Integer.MAX_VALUE);
  }
}