package info.bitrich.xchangestream.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import info.bitrich.xchangestream.dto.WrapCurrency;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.marketdata.KlineInterval;
import org.knowm.xchange.instrument.Instrument;

/**
 * Use to specify subscriptions during the connect phase For instancing, use builder @link {@link
 * ProductSubscriptionBuilder}
 */
public class ProductSubscription {
  private final List<Instrument> orderBook;
  private final List<Instrument> trades;
  private final List<Instrument> ticker;
  private final List<WrapCurrency> klines;
  private final List<Instrument> userTrades;
  private final List<Instrument> orders;
  private final List<Currency> balances;


  private ProductSubscription(ProductSubscriptionBuilder builder) {
    this.orderBook = asList(builder.orderBook);
    this.trades = asList(builder.trades);
    this.ticker = asList(builder.ticker);
    this.orders = asList(builder.orders);
    this.userTrades = asList(builder.userTrades);
    this.balances = asList(builder.balances);
    this.klines = asList(builder.klines);
  }

  private <T> List<T> asList(Iterable<T> collection) {
    List<T> result = new ArrayList<>();
    collection.forEach(result::add);
    return Collections.unmodifiableList(result);
  }

  public List<Instrument> getOrderBook() {
    return orderBook;
  }

  public List<Instrument> getTrades() {
    return trades;
  }

  public List<Instrument> getTicker() {
    return ticker;
  }

  public List<Instrument> getOrders() {
    return orders;
  }

  public List<Instrument> getUserTrades() {
    return userTrades;
  }

  public List<Currency> getBalances() {
    return balances;
  }

  public boolean isEmpty() {
    return !hasAuthenticated() && !hasUnauthenticated();
  }

  public boolean hasAuthenticated() {
    return !orders.isEmpty() || !userTrades.isEmpty() || !balances.isEmpty();
  }

  public boolean hasUnauthenticated() {
    return !ticker.isEmpty() || !trades.isEmpty() || !orderBook.isEmpty() || !klines.isEmpty();
  }

  public static ProductSubscriptionBuilder create() {
    return new ProductSubscriptionBuilder();
  }

  public List<WrapCurrency> getKlines() {
    return klines;
  }

  public static class ProductSubscriptionBuilder {
    private final Set<Instrument> orderBook;
    private final Set<Instrument> trades;
    private final Set<Instrument> ticker;
    private final Set<WrapCurrency> klines;
    private final Set<Instrument> userTrades;
    private final Set<Instrument> orders;
    private final Set<Currency> balances;

    private ProductSubscriptionBuilder() {
      orderBook = new HashSet<>();
      trades = new HashSet<>();
      ticker = new HashSet<>();
      orders = new HashSet<>();
      userTrades = new HashSet<>();
      balances = new HashSet<>();
      klines = new HashSet<>();
    }

    public ProductSubscriptionBuilder addOrderbook(Instrument pair) {
      orderBook.add(pair);
      return this;
    }

    public ProductSubscriptionBuilder addTrades(Instrument pair) {
      trades.add(pair);
      return this;
    }

    public ProductSubscriptionBuilder addTicker(Instrument pair) {
      ticker.add(pair);
      return this;
    }

    public ProductSubscriptionBuilder addOrders(Instrument pair) {
      orders.add(pair);
      return this;
    }

    public ProductSubscriptionBuilder addUserTrades(Instrument pair) {
      userTrades.add(pair);
      return this;
    }

    public ProductSubscriptionBuilder addBalances(Currency pair) {
      balances.add(pair);
      return this;
    }

    public ProductSubscriptionBuilder addKlines(WrapCurrency pair) {
      klines.add(pair);
      return this;
    }

    public ProductSubscriptionBuilder addAll(Instrument pair) {
      orderBook.add(pair);
      trades.add(pair);
      ticker.add(pair);
      orders.add(pair);
      userTrades.add(pair);
      klines.add(new WrapCurrency(pair, KlineInterval.h1));
      if (pair instanceof CurrencyPair){
        balances.add(((CurrencyPair) pair).base);
        balances.add(((CurrencyPair) pair).counter);
      }else if (pair instanceof FuturesContract){

      }

      return this;
    }

    public ProductSubscription build() {
      return new ProductSubscription(this);
    }
  }
}
