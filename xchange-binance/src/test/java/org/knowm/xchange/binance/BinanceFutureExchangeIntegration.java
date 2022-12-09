package org.knowm.xchange.binance;

import org.junit.BeforeClass;
import org.junit.Test;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.binance.dto.meta.exchangeinfo.BinanceExchangeInfo;

public class BinanceFutureExchangeIntegration {
  protected static BinanceCoinFutureExchange binanceCoinFutureExchange;
  protected static BinanceUsdsFutureExchange binanceUsdsFutureExchange;

  @BeforeClass
  public static void beforeClass() throws Exception {
    createExchange();
  }


  protected static void createExchange() throws Exception {
    binanceCoinFutureExchange = ExchangeFactory.INSTANCE.createExchange(BinanceCoinFutureExchange.class);
    binanceUsdsFutureExchange = ExchangeFactory.INSTANCE.createExchange(BinanceUsdsFutureExchange.class);
  }

  @Test
  public void getExchangeInfo(){

    BinanceExchangeInfo binanceExchangeInfo =  binanceCoinFutureExchange.getExchangeInfo();
    System.out.println();
    BinanceExchangeInfo binanceExchangeInfo1 =  binanceUsdsFutureExchange.getExchangeInfo();
    System.out.println();

  }
}
