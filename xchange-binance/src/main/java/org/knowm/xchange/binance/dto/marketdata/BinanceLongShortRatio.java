package org.knowm.xchange.binance.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public final class BinanceLongShortRatio {

  public final String symbol;
  public final BigDecimal longShortRatio;
  public final BigDecimal longAccount;
  public final BigDecimal shortAccount;
  public final long timestamp;

  public BinanceLongShortRatio(
      @JsonProperty("symbol") String symbol,
      @JsonProperty("longShortRatio") BigDecimal longShortRatio,
      @JsonProperty("longAccount") BigDecimal longAccount,
      @JsonProperty("shortAccount") BigDecimal shortAccount,
      @JsonProperty("timestamp") long timestamp
      ) {
    this.symbol = symbol;
    this.longShortRatio = longShortRatio;
    this.longAccount = longAccount;
    this.shortAccount = shortAccount;
    this.timestamp = timestamp;
  }
}
