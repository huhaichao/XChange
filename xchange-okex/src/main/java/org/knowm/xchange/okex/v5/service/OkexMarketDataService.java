package org.knowm.xchange.okex.v5.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.*;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.okex.v5.OkexAdapters;
import org.knowm.xchange.okex.v5.OkexExchange;
import org.knowm.xchange.okex.v5.dto.OkexException;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.utils.DateUtils;

/** Author: Max Gao (gaamox@tutanota.com) Created: 08-06-2021 */
public class OkexMarketDataService extends OkexMarketDataServiceRaw implements MarketDataService {
  public static final String SPOT = "SPOT";
  public static final String SWAP = "SWAP";
  public static final String FUTURES = "FUTURES";
  public static final String OPTION = "OPTION";

  public OkexMarketDataService(OkexExchange exchange, ResilienceRegistries resilienceRegistries) {
    super(exchange, resilienceRegistries);
  }

  @Override
  public OrderBook getOrderBook(Instrument instrument, Object... args) throws IOException {
    return OkexAdapters.adaptOrderBook(
        getOkexOrderbook(OkexAdapters.adaptCurrencyPairId(instrument)), instrument);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
    return this.getOrderBook((Instrument) currencyPair, args);
  }

  @Override
  public Trades getTrades(Instrument instrument, Object... args) throws IOException {
    return OkexAdapters.adaptTrades(
        getOkexTrades(OkexAdapters.adaptCurrencyPairId(instrument), 100).getData(), instrument);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {
    return OkexAdapters.adaptTrades(
        getOkexTrades(OkexAdapters.adaptCurrencyPairId(currencyPair), 100).getData(), currencyPair);
  }

  @Override
  public Klines getKlines(CurrencyPair currencyPair, Object... args) throws IOException {
    String start = null;
    String end = null;
    KlineInterval klineInterval = (KlineInterval)args[0];
    Integer limit = 300;
    if (args != null && args.length >=2 ){
      limit = (Integer)args[1];
    }
    if (limit > 300){
      limit = 300;
    }
    if (args != null && args.length == 4){
      if (args[2] instanceof String) {
        start = (String)args[2];
      }
      if (args[3] instanceof String) {
        end = (String)args[3];
      }
    }
    return new Klines(exchange.getExchangeType(),
            OkexAdapters.adaptCandles(getCandles(OkexAdapters.adaptCurrencyPairId(currencyPair),end,start,klineInterval.getCodeSimple(),String.valueOf(limit)).getData()),
            currencyPair,
            klineInterval,
            limit,
            start ==null ? null: DateUtils.fromISODateString(start),end==null ? null:DateUtils.fromISODateString(end));
  }
}
