package org.knowm.xchange.binance;

import org.junit.BeforeClass;
import org.junit.Test;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.binance.dto.meta.exchangeinfo.BinanceExchangeInfo;
import org.knowm.xchange.dto.meta.ExchangeMetaData;

public class BinanceFutureExchangeIntegration {
  protected static BinanceCoinFutureExchange binanceCoinFutureExchange;
  protected static BinanceUsdsFutureExchange binanceUsdsFutureExchange;

  @BeforeClass
  public static void beforeClass() throws Exception {
    createExchange();
  }


  protected static void createExchange() throws Exception {
    //binanceCoinFutureExchange = ExchangeFactory.INSTANCE.createExchange(BinanceCoinFutureExchange.class);
    binanceUsdsFutureExchange = ExchangeFactory.INSTANCE.createExchange(BinanceUsdsFutureExchange.class);
  }

  @Test
  public void getExchangeInfo(){

   /* ExchangeMetaData binanceExchangeInfo =  binanceCoinFutureExchange.getExchangeMetaData();
    System.out.println();*/
    ExchangeMetaData binanceExchangeInfo1 =  binanceUsdsFutureExchange.getExchangeMetaData();
    System.out.println();

  }
}
