package org.knowm.xchange.okex.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@NoArgsConstructor
public class OkexFundingRate {

    @JsonProperty("instType")
    private String instrumentType;

    @JsonProperty("instId")
    private String instrumentId;

    @JsonProperty("fundingRate")
    private BigDecimal fundingRate;

    @JsonProperty("nextFundingRate")
    private BigDecimal nextFundingRate;

    @JsonProperty("fundingTime")
    private Date fundingTime;

    @JsonProperty("nextFundingTime")
    private Date nextFundingTime;

}
