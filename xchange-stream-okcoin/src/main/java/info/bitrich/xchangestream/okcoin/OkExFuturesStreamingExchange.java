package info.bitrich.xchangestream.okcoin;

import org.knowm.xchange.ExchangeType;

public class OkExFuturesStreamingExchange extends OkExStreamingExchange {

  @Override
  public ExchangeType getExchangeType(){
    return ExchangeType.OKEXFUTURESSTREAM;
  }

  private static final String API_URI = "wss://real.okex.com:10440/websocket/okexapi?compress=true";

  public OkExFuturesStreamingExchange() {
    super(API_URI);
  }
}
