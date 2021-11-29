package org.knowm.xchange.dto.marketdata;

import java.util.Arrays;

import static java.util.concurrent.TimeUnit.*;
public enum KlineInterval {
  m1("1min","1m", MINUTES.toSeconds(1), MINUTES.toMillis(1)),
  m3("3min", "3m",MINUTES.toSeconds(3), MINUTES.toMillis(3)),
  m5("5min", "5m", MINUTES.toSeconds(5), MINUTES.toMillis(5)),
  m15("15min", "15m",MINUTES.toSeconds(15),  MINUTES.toMillis(15)),
  m30("30min", "30m",MINUTES.toSeconds(30),  MINUTES.toMillis(30)),

  h1("60min", "1h",HOURS.toSeconds(1), HOURS.toMillis(1)),
  h2("2hour", "2h", HOURS.toSeconds(2), HOURS.toMillis(2)),
  h4("4hour", "4h", HOURS.toSeconds(4), HOURS.toMillis(4)),
  h6("6hour", "6h", HOURS.toSeconds(6), HOURS.toMillis(6)),
  h8("8hour", "8h", HOURS.toSeconds(8), HOURS.toMillis(8)),
  h12("12hour", "12h", HOURS.toSeconds(12), HOURS.toMillis(12)),

  d1("1day", "1d", DAYS.toSeconds(1), DAYS.toMillis(1)),
  d3("3day", "3d", DAYS.toSeconds(3), DAYS.toMillis(3)),
  w1("1week", "1w", DAYS.toSeconds(7), DAYS.toMillis(7)),
  M1("1mon", "1M", DAYS.toSeconds(30), DAYS.toMillis(30)),
  Y1("1year", "1y", DAYS.toSeconds(365), DAYS.toMillis(365));

  private final String code;
  private final String codeSimple;
  private final Long seconds;
  private final Long millis;

  public static KlineInterval parseFromCode(String code){
    return Arrays.stream(KlineInterval.values())
            .filter(klineInterval -> klineInterval.getCode().equals(code) || klineInterval.getCodeSimple().equals(code))
            .findFirst()
            .get();
  }
  private KlineInterval(String code, String codeSimple, Long seconds, Long millis) {
    this.seconds = seconds;
    this.code = code;
    this.codeSimple = codeSimple;
    this.millis = millis;
  }

  public Long getSeconds() {
    return seconds;
  }

  public String getCode() {
    return code;
  }

  public String getCodeSimple() {
    return codeSimple;
  }

  public Long getMillis() {
    return millis;
  }
}
