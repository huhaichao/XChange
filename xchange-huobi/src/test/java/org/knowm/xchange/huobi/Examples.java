package org.knowm.xchange.huobi;

import java.util.Arrays;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.KlineInterval;

public class Examples {

  public static void main(String... args) throws Exception {

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(HuobiExchange.class);

   /* HuobiMarketDataService marketDataService =
        (HuobiMarketDataService) exchange.getMarketDataService();*/

    // GET Klines
    exchange.getMarketDataService().getKlines(CurrencyPair.DASH_USDT, KlineInterval.h1, 2000).getKlines()
        .forEach(System.out::println);
  }
}
