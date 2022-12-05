package org.knowm.xchange.okex.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;

public class OkexCandleStick {

  private final Long timestamp;
  private final String openPrice;
  private final String closePrice;
  private final String highPrice;
  private final String lowPrice;
  private final String volume;
  private final String volumeCcy;
  private final String quoteAssetVolume;


  @JsonCreator
  public OkexCandleStick(JsonNode node) {
    this.timestamp = node.get(0).asLong();
    this.openPrice = node.get(1).asText();
    this.closePrice = node.get(4).asText();
    this.highPrice = node.get(2).asText();
    this.lowPrice = node.get(3).asText();
    this.volume = node.get(5).asText();
    this.quoteAssetVolume = node.get(6).asText();
    this.volumeCcy = node.get(6).asText();
  }

  public Long getTimestamp() {
    return timestamp;
  }

  public String getOpenPrice() {
    return openPrice;
  }

  public String getClosePrice() {
    return closePrice;
  }

  public String getHighPrice() {
    return highPrice;
  }

  public String getLowPrice() {
    return lowPrice;
  }

  public String getVolume() {
    return volume;
  }

  @Override
  public String toString() {
    return "OkexCandleStick{"
        + "timestamp="
        + timestamp
        + ", openPrice='"
        + openPrice
        + '\''
        + ", closePrice='"
        + closePrice
        + '\''
        + ", highPrice='"
        + highPrice
        + '\''
        + ", lowPrice='"
        + lowPrice
        + '\''
        + ", volume='"
        + volume
        + '\''
        + ", quoteAssetVolume='"
        + quoteAssetVolume
        + '\''
        + '}';
  }

  public String getVolumeCcy() {
    return volumeCcy;
  }

  public String getQuoteAssetVolume() {
    return quoteAssetVolume;
  }
}
