package org.knowm.xchange.okcoin.service.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.util.DateUtil;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.KlineInterval;
import org.knowm.xchange.dto.marketdata.Klines;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.okcoin.OkCoinExchange;
import org.knowm.xchange.okcoin.OkexExchangeV3;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.utils.DateUtils;

import java.io.IOException;

/** @author timmolter */
public class TickerIntegration {

  @Test
  public void tickerFetchChinaTest() throws Exception {
    ExchangeSpecification exSpec = new ExchangeSpecification(OkCoinExchange.class);
    exSpec.setExchangeSpecificParametersItem("Use_Intl", false);

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(exSpec);
    MarketDataService marketDataService = exchange.getMarketDataService();
    Ticker ticker = marketDataService.getTicker(new CurrencyPair("BTC", "CNY"));
    System.out.println(ticker.toString());
    assertThat(ticker).isNotNull();
  }

  @Test
  public void tickerFetchIntlTest() throws Exception {
    ExchangeSpecification exSpec = new ExchangeSpecification(OkCoinExchange.class);
    exSpec.setExchangeSpecificParametersItem("Use_Intl", true);

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(exSpec);
    MarketDataService marketDataService = exchange.getMarketDataService();
    Ticker ticker = marketDataService.getTicker(new CurrencyPair("BTC", "USD"));
    System.out.println(ticker.toString());
    assertThat(ticker).isNotNull();
  }

  @Test
  public void  getKLines() throws IOException {
    ExchangeSpecification exSpec = new ExchangeSpecification(OkexExchangeV3.class);
    //exSpec.setExchangeSpecificParametersItem("Use_Intl", true);
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(exSpec);
    Klines klines = exchange.getMarketDataService().getKlines(CurrencyPair.BTC_USDT, DateUtils.toISODateString(DateUtil.yesterday()), DateUtils.toISODateString(DateUtil.now()),KlineInterval.h1);
    assertThat(klines).isNotNull();
  }

  @Test
  public void  getHistoryKLines() throws IOException {
    ExchangeSpecification exSpec = new ExchangeSpecification(OkexExchangeV3.class);
    //exSpec.setExchangeSpecificParametersItem("Use_Intl", true);
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(exSpec);
    Klines klines = exchange.getMarketDataService().getHistoryKlines(CurrencyPair.BTC_USDT, "2019-09-25T02:31:00.000Z", "2019-09-24T02:31:00.000Z",KlineInterval.h1,300);
    assertThat(klines).isNotNull();
  }
}
