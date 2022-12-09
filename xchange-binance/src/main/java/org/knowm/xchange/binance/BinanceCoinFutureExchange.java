package org.knowm.xchange.binance;

import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.ExchangeType;
import org.knowm.xchange.binance.dto.account.AssetDetail;
import org.knowm.xchange.binance.service.BinanceMarketDataService;
import org.knowm.xchange.binance.service.BinanceTradeService;
import org.knowm.xchange.binance.service.BinanceUsAccountService;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.utils.AuthUtils;

import java.io.IOException;
import java.util.Map;

public class BinanceCoinFutureExchange extends BinanceExchange {

  @Override
  public ExchangeType getExchangeType(){
    return ExchangeType.BINANCECOINFUTURE;
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification spec = new ExchangeSpecification(this.getClass());
    spec.setSslUri("https://dapi.binance.com");
    spec.setHost("www.binance.us");
    spec.setPort(80);
    spec.setExchangeName("Binance");
    spec.setExchangeDescription("Binance Coin Future Exchange.");
    AuthUtils.setApiAndSecretKey(spec, "binancecoinfuture");
    return spec;
  }

  @Override
  protected void initServices() {
    this.binance =
        ExchangeRestProxyBuilder.forInterface(
                        BinanceCoinFutureAuthenticated.class, getExchangeSpecification())
            .build();
    this.timestampFactory =
        new BinanceTimestampFactory(
            binance, getExchangeSpecification().getResilience(), getResilienceRegistries());
    this.marketDataService = new BinanceMarketDataService(this, binance, getResilienceRegistries());
    this.tradeService = new BinanceTradeService(this, binance, getResilienceRegistries());
    this.accountService = new BinanceUsAccountService(this, binance, getResilienceRegistries());
  }

  @Override
  public void remoteInit() {
    BinanceMarketDataService marketDataService = (BinanceMarketDataService) this.marketDataService;
    try {
      exchangeInfo = marketDataService.getExchangeInfo();
    } catch (IOException e) {
      e.printStackTrace();
    }

    Map<String, AssetDetail> assetDetailMap = null;

    postInit(assetDetailMap);
  }
}
