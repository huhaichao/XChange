package org.knowm.xchange.okex.v5;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import org.junit.Assert;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.KlineInterval;
import org.knowm.xchange.dto.marketdata.Klines;
import org.knowm.xchange.okex.OkexExchange;
import org.knowm.xchange.okex.dto.OkexResponse;
import org.knowm.xchange.okex.dto.marketdata.OkexCandleStick;
import org.knowm.xchange.okex.service.OkexMarketDataService;

public class OkexMarketDataIntegrationTest {

  @Test
  public void testCandleHist() throws IOException {
    Exchange exchange =
        ExchangeFactory.INSTANCE.createExchange(OkexExchange.class.getCanonicalName());
    exchange.remoteInit();
    ((OkexMarketDataService) exchange.getMarketDataService()).getOkexOrderbook("BTC/USDC");
    OkexResponse<List<OkexCandleStick>> barHistDtos =
        ((OkexMarketDataService) exchange.getMarketDataService())
            .getHistoryCandle("BTC-USDC", null, null, null, null);
    Assert.assertTrue(Objects.nonNull(barHistDtos) && !barHistDtos.getData().isEmpty());
  }

  @Test
  public void testCandles() throws IOException {
    Exchange exchange =
            ExchangeFactory.INSTANCE.createExchange(OkexExchange.class.getCanonicalName());
    exchange.remoteInit();
    ((OkexMarketDataService) exchange.getMarketDataService()).getOkexOrderbook("BTC/USDC");
    OkexResponse<List<OkexCandleStick>> barHistDtos =
            ((OkexMarketDataService) exchange.getMarketDataService())
                    .getCandles("BTC-USDC", null, null, null, null);
    Assert.assertTrue(Objects.nonNull(barHistDtos) && !barHistDtos.getData().isEmpty());
  }

  @Test
  public void testKlines() throws IOException {
    Exchange exchange =
            ExchangeFactory.INSTANCE.createExchange(OkexExchange.class.getCanonicalName());
    exchange.remoteInit();
    Klines klines = exchange.getMarketDataService().getKlines(CurrencyPair.BTC_USDT, KlineInterval.h1);
    Assert.assertTrue(Objects.nonNull(klines) && !klines.getKlines().isEmpty());
  }
}
