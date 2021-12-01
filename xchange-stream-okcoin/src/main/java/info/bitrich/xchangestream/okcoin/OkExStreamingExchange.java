package info.bitrich.xchangestream.okcoin;

import org.knowm.xchange.ExchangeType;

/** Created by Lukas Zaoralek on 17.11.17. */
public class OkExStreamingExchange extends OkCoinStreamingExchange {
  private static final String API_URI = "wss://real.okex.com:8443/ws/v3";

  @Override
  public ExchangeType getExchangeType(){
    return ExchangeType.OKCOINSTREAM;
  }

  public OkExStreamingExchange() {
    super(new OkCoinStreamingService(API_URI));
  }

  public OkExStreamingExchange(String apiUrl) {
    super(new OkCoinStreamingService(apiUrl));
  }
}
