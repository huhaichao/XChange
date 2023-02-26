package org.knowm.xchange.okex.service;

import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.CandleStickData;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.dto.marketdata.*;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.okex.OkexAdapters;
import org.knowm.xchange.okex.OkexExchange;
import org.knowm.xchange.okex.dto.OkexResponse;
import org.knowm.xchange.okex.dto.marketdata.OkexCandleStick;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.trade.params.CandleStickDataParams;
import org.knowm.xchange.service.trade.params.DefaultCandleStickParam;
import org.knowm.xchange.service.trade.params.DefaultCandleStickParamWithLimit;
import org.knowm.xchange.utils.DateUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

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
        getOkexOrderbook(OkexAdapters.adaptInstrumentToOkexInstrumentId(instrument)), instrument);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
    return this.getOrderBook((Instrument) currencyPair, args);
  }

  @Override
  public Trades getTrades(Instrument instrument, Object... args) throws IOException {
    return OkexAdapters.adaptTrades(
        getOkexTrades(OkexAdapters.adaptInstrumentToOkexInstrumentId(instrument), 100).getData(), instrument);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {
    return OkexAdapters.adaptTrades(
        getOkexTrades(OkexAdapters.adaptInstrumentToOkexInstrumentId(currencyPair), 100).getData(), currencyPair);
  }


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
    String bar = klineInterval.getCodeSimple();
    if (!bar.endsWith("m")){
      bar = bar.toUpperCase();
    }
    return new Klines(exchange.getExchangeType(),
            OkexAdapters.adaptCandles(getCandles(OkexAdapters.adaptInstrumentToOkexInstrumentId(currencyPair),end,start,bar,String.valueOf(limit)).getData()),
            currencyPair,
            klineInterval,
            limit,
            start ==null ? null: DateUtils.fromISODateString(start),end==null ? null:DateUtils.fromISODateString(end));
  }

  @Override
  public Klines getKlines(Instrument instrument, Object... args) throws IOException {
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
    String bar = klineInterval.getCodeSimple();
    if (!bar.endsWith("m")){
      bar = bar.toUpperCase();
    }
    return new Klines(exchange.getExchangeType(),
            OkexAdapters.adaptCandles(getCandles(OkexAdapters.adaptInstrumentToOkexInstrumentId(instrument),end,start,bar,String.valueOf(limit)).getData()),
            instrument,
            klineInterval,
            limit,
            start ==null ? null: DateUtils.fromISODateString(start),end==null ? null:DateUtils.fromISODateString(end));
  }

  @Override
  public CandleStickData getCandleStickData(CurrencyPair currencyPair, CandleStickDataParams params)
          throws IOException {

    if (!(params instanceof DefaultCandleStickParam)) {
      throw new NotYetImplementedForExchangeException("Only DefaultCandleStickParam is supported");
    }
    DefaultCandleStickParam defaultCandleStickParam = (DefaultCandleStickParam) params;
    OkexCandleStickPeriodType periodType =
            OkexCandleStickPeriodType.getPeriodTypeFromSecs(defaultCandleStickParam.getPeriodInSecs());
    if (periodType == null) {
      throw new NotYetImplementedForExchangeException("Only discrete period values are supported;" +
              Arrays.toString(OkexCandleStickPeriodType.getSupportedPeriodsInSecs()));
    }

    String limit = null;
    if (params instanceof DefaultCandleStickParamWithLimit) {
      limit = String.valueOf(((DefaultCandleStickParamWithLimit) params).getLimit());
    }

    OkexResponse<List<OkexCandleStick>> historyCandle = getHistoryCandle(
            OkexAdapters.adaptInstrumentToOkexInstrumentId(currencyPair),
            String.valueOf(defaultCandleStickParam.getEndDate().getTime()),
            String.valueOf(defaultCandleStickParam.getStartDate().getTime()),
            periodType.getFieldValue(), limit);
    return OkexAdapters.adaptCandleStickData(historyCandle.getData(), currencyPair);
  }
}
