package info.bitrich.xchangestream.binance;

import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import info.bitrich.xchangestream.dto.WrapCurrency;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.KlineInterval;

public class BinanceStreamKlineRealtimeExample {

  public static void main(String[] args) throws InterruptedException {
    final ExchangeSpecification exchangeSpecification =
        new ExchangeSpecification(BinanceStreamingExchange.class);
    exchangeSpecification.setShouldLoadRemoteMetaData(false);
    BinanceStreamingExchange exchange =(BinanceStreamingExchange)
        StreamingExchangeFactory.INSTANCE.createExchange(exchangeSpecification);
    ProductSubscription subscription = ProductSubscription.create()
            .addKlines(new WrapCurrency(CurrencyPair.BTC_USDT, KlineInterval.m5))
            .build();
    exchange.connect(subscription).blockingAwait();
    exchange
        .getStreamingMarketDataService()
        .getKlines(CurrencyPair.BTC_USDT,KlineInterval.m5.getCode())
        .blockingSubscribe(System.out::println);
  }
}