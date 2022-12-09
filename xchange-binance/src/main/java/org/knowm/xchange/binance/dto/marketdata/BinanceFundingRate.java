package org.knowm.xchange.binance.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public final class BinanceFundingRate {

  public final String symbol;
  public final BigDecimal fundingRate;
  public final long fundingTime;

  public BinanceFundingRate(
      @JsonProperty("symbol") String symbol,
      @JsonProperty("fundingRate") BigDecimal fundingRate,
      @JsonProperty("fundingTime") long fundingTime
      ) {
    this.symbol = symbol;
    this.fundingRate = fundingRate;
    this.fundingTime = fundingTime;
  }
}
