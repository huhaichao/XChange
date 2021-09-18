package org.knowm.xchange.okcoin.v3.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.*;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.okcoin.OkCoinException;
import org.knowm.xchange.okcoin.OkexAdaptersV3;
import org.knowm.xchange.okcoin.OkexExchangeV3;
import org.knowm.xchange.okcoin.v3.dto.marketdata.OkexOrderBook;
import org.knowm.xchange.okcoin.v3.dto.marketdata.OkexSpotTicker;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.marketdata.params.Params;
import org.knowm.xchange.utils.DateUtils;

public class OkexMarketDataService extends OkexMarketDataServiceRaw implements MarketDataService {

  public OkexMarketDataService(OkexExchangeV3 exchange) {
    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {
    OkexSpotTicker tokenPairInformation =
        okex.getSpotTicker(OkexAdaptersV3.toSpotInstrument(currencyPair));
    return OkexAdaptersV3.convert(tokenPairInformation);
  }

  @Override
  public List<Ticker> getTickers(Params params) throws IOException {
    return okex.getAllSpotTickers().stream()
        .map(OkexAdaptersV3::convert)
        .collect(Collectors.toList());
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair pair, Object... args) throws IOException {

    int limitDepth = 50;

    if (args != null && args.length == 1) {
      Object arg0 = args[0];
      if (!(arg0 instanceof Integer)) {
        throw new IllegalArgumentException("Argument 0 must be an Integer!");
      } else {
        limitDepth = (Integer) arg0;
      }
    }
    OkexOrderBook okexOrderbook =
        okex.getOrderBook(OkexAdaptersV3.toSpotInstrument(pair), limitDepth);
    return OkexAdaptersV3.convertOrderBook(okexOrderbook, pair);
  }

  @Override
  public Klines getKlines(CurrencyPair currencyPair, Object... args) throws IOException {
         String start = null;
         String end = null;
         KlineInterval klineInterval = KlineInterval.h1;
         if (args != null && args.length == 3){
            if (args[0] instanceof String) {
              start = (String)args[0];
            }
           if (args[1] instanceof String) {
              end = (String)args[1];
           }
           if (args[2] instanceof KlineInterval){
             klineInterval = (KlineInterval)args[2];
           }
         }
         Long granularity = klineInterval.getSeconds();
         return new Klines(okex.getKlines(OkexAdaptersV3.toSpotInstrument(currencyPair),start,end,granularity)
                 .stream().map( kline ->{
                   try {
                     return new Kline.Builder()
                             .openTime(DateUtils.fromISODateString(String.valueOf(kline[0])).getTime())
                             .open( new BigDecimal(String.valueOf(kline[1])))
                             .high(new BigDecimal(String.valueOf(kline[2])))
                             .low(new BigDecimal(String.valueOf(kline[3])))
                             .close(new BigDecimal(String.valueOf(kline[4])))
                             .amount(new BigDecimal(String.valueOf(kline[5])))
                             .build();
                   } catch (InvalidFormatException e) {
                      throw new OkCoinException(0,e.getMessage());
                   }
                 }).collect(Collectors.toList()),currencyPair,klineInterval,200, DateUtils.fromISODateString(start).getTime(),DateUtils.fromISODateString(end).getTime());
  }

  @Override
  public Klines getHistoryKlines(CurrencyPair currencyPair, Object... args) throws IOException {
        String start = null;
        String end = null;
        KlineInterval klineInterval = KlineInterval.h1;;
        Integer  limit = 300;
        if (args != null && args.length == 4){
          if (args[0] instanceof String) {
            start = (String)args[0];
          }
          if (args[1] instanceof String) {
            end = (String)args[1];
          }
          if (args[2] instanceof KlineInterval){
            klineInterval = (KlineInterval)args[2];
          }
          limit = (Integer)args[3];
        }
        Long granularity = klineInterval.getSeconds();
        return new Klines(okex.getHistoryKlines(OkexAdaptersV3.toSpotInstrument(currencyPair),start,end,granularity,limit)
                .stream().map( kline ->{
                  try {
                    return new Kline.Builder()
                            .openTime(DateUtils.fromISODateString(String.valueOf(kline[0])).getTime())
                            .open( new BigDecimal(String.valueOf(kline[1])))
                            .high(new BigDecimal(String.valueOf(kline[2])))
                            .low(new BigDecimal(String.valueOf(kline[3])))
                            .close(new BigDecimal(String.valueOf(kline[4])))
                            .amount(new BigDecimal(String.valueOf(kline[5])))
                            .build();
                  } catch (InvalidFormatException e) {
                    throw new OkCoinException(0,e.getMessage());
                  }
                }).collect(Collectors.toList()),currencyPair,klineInterval,limit,DateUtils.fromISODateString(start).getTime(),DateUtils.fromISODateString(end).getTime());
  }
}
