package org.knowm.xchange.binance.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.knowm.xchange.binance.BinanceAdapters;
import org.knowm.xchange.binance.BinanceAuthenticated;
import org.knowm.xchange.binance.BinanceErrorAdapter;
import org.knowm.xchange.binance.BinanceExchange;
import org.knowm.xchange.binance.dto.BinanceException;
import org.knowm.xchange.binance.dto.marketdata.*;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.*;
import org.knowm.xchange.dto.marketdata.KlineInterval;
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.marketdata.params.Params;
import org.knowm.xchange.utils.DateUtils;

public class BinanceMarketDataService extends BinanceMarketDataServiceRaw
    implements MarketDataService {

  public BinanceMarketDataService(
      BinanceExchange exchange,
      BinanceAuthenticated binance,
      ResilienceRegistries resilienceRegistries) {
    super(exchange, binance, resilienceRegistries);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair pair, Object... args) throws IOException {
    try {
      int limitDepth = 100;

      if (args != null && args.length == 1) {
        Object arg0 = args[0];
        if (!(arg0 instanceof Integer)) {
          throw new ExchangeException("Argument 0 must be an Integer!");
        } else {
          limitDepth = (Integer) arg0;
        }
      }
      BinanceOrderbook binanceOrderbook = getBinanceOrderbook(pair, limitDepth);
      return convertOrderBook(binanceOrderbook, pair);
    } catch (BinanceException e) {
      throw BinanceErrorAdapter.adapt(e);
    }
  }

  public static OrderBook convertOrderBook(BinanceOrderbook ob, CurrencyPair pair) {
    List<LimitOrder> bids =
        ob.bids.entrySet().stream()
            .map(e -> new LimitOrder(OrderType.BID, e.getValue(), pair, null, null, e.getKey()))
            .collect(Collectors.toList());
    List<LimitOrder> asks =
        ob.asks.entrySet().stream()
            .map(e -> new LimitOrder(OrderType.ASK, e.getValue(), pair, null, null, e.getKey()))
            .collect(Collectors.toList());
    return new OrderBook(null, asks, bids);
  }

  @Override
  public Ticker getTicker(CurrencyPair pair, Object... args) throws IOException {
    try {
      return ticker24h(pair).toTicker();
    } catch (BinanceException e) {
      throw BinanceErrorAdapter.adapt(e);
    }
  }

  @Override
  public List<Ticker> getTickers(Params params) throws IOException {
    try {
      return ticker24h().stream().map(BinanceTicker24h::toTicker).collect(Collectors.toList());
    } catch (BinanceException e) {
      throw BinanceErrorAdapter.adapt(e);
    }
  }

  /**
   * optional parameters provided in the args array:
   *
   * <ul>
   *   <li>0: Long fromId optional, ID to get aggregate trades from INCLUSIVE.
   *   <li>1: Long startTime optional, Timestamp in ms to get aggregate trades from INCLUSIVE.
   *   <li>2: Long endTime optional, Timestamp in ms to get aggregate trades until INCLUSIVE.
   *   <li>3: Integer limit optional, Default 500; max 500.
   * </ul>
   *
   * <p>
   */
  @Override
  public Trades getTrades(CurrencyPair pair, Object... args) throws IOException {
    try {
      Long fromId = tradesArgument(args, 0, Long::valueOf);
      Long startTime = tradesArgument(args, 1, Long::valueOf);
      Long endTime = tradesArgument(args, 2, Long::valueOf);
      Integer limit = tradesArgument(args, 3, Integer::valueOf);
      List<BinanceAggTrades> aggTrades =
          binance.aggTrades(BinanceAdapters.toSymbol(pair), fromId, startTime, endTime, limit);
      List<Trade> trades =
          aggTrades.stream()
              .map(
                  at ->
                      new Trade.Builder()
                          .type(BinanceAdapters.convertType(at.buyerMaker))
                          .originalAmount(at.quantity)
                          .currencyPair(pair)
                          .price(at.price)
                          .timestamp(at.getTimestamp())
                          .id(Long.toString(at.aggregateTradeId))
                          .build())
              .collect(Collectors.toList());
      return new Trades(trades, TradeSortType.SortByTimestamp);
    } catch (BinanceException e) {
      throw BinanceErrorAdapter.adapt(e);
    }
  }

  @Override
  public Klines getKlines(CurrencyPair currencyPair, Object... args) throws IOException {
    KlineInterval klineInterval = null ;
    Integer limit = null ;
    Long startTime = null;
    Long endTime  = null;
    if (args == null || args.length <2) {
      throw new ExchangeException("args can not be null or length less than 2");
    }
    if(args[0] instanceof KlineInterval){
      klineInterval = (KlineInterval)args[0];
    }else {
      throw new ExchangeException("args[0] is not be KlineInterval");
    }
    limit = (Integer)args[1];
    if(args.length == 4){
      if(args[2] != null){
        startTime = (Long)args[2];
      }
      if(args[3] != null){
        endTime = (Long)args[2];
      }
    }

    List<BinanceKline> binanceKlines = klines(currencyPair,klineInterval,limit,startTime,endTime);

    return new Klines(exchange.getExchangeType(),binanceKlines.stream().map(binanceKline -> {
      Date openTime = DateUtils.fromMillisUtc(binanceKline.getOpenTime());
      return new Kline.Builder()
              .id(openTime.getTime())
              .openTime(openTime)
              .closeTime(DateUtils.fromMillisUtc(binanceKline.getCloseTime()))
              .open(binanceKline.getOpenPrice())
              .close(binanceKline.getClosePrice())
              .high(binanceKline.getHighPrice())
              .low(binanceKline.getLowPrice())
              .amount(binanceKline.getVolume())
              .vol(binanceKline.getQuoteAssetVolume())
              .count(binanceKline.getNumberOfTrades())
              .build();
    }).collect(Collectors.toList())
            ,currencyPair
            ,klineInterval
            ,limit
            ,startTime == null ? null : DateUtils.fromMillisUtc(startTime)
            ,startTime == null ? null : DateUtils.fromMillisUtc(endTime));
  }

  private <T extends Number> T tradesArgument(
      Object[] args, int index, Function<String, T> converter) {
    if (index >= args.length) {
      return null;
    }
    Object arg = args[index];
    if (arg == null) {
      return null;
    }
    String argStr = arg.toString();
    try {
      return converter.apply(argStr);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException(
          "Argument on index " + index + " is not a number: " + argStr, e);
    }
  }

  public List<Ticker> getAllBookTickers() throws IOException {
    List<BinancePriceQuantity> binanceTickers = tickerAllBookTickers();
    return BinanceAdapters.adaptPriceQuantities(binanceTickers);
  }
}
