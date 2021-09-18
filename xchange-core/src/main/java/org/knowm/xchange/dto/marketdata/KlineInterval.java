package org.knowm.xchange.dto.marketdata;

import static java.util.concurrent.TimeUnit.*;

public enum KlineInterval {
  m1("1min", MINUTES.toSeconds(1)),
  m3("3min", MINUTES.toSeconds(3)),
  m5("5min", MINUTES.toSeconds(5)),
  m15("15min", MINUTES.toSeconds(15)),
  m30("30min", MINUTES.toSeconds(30)),

  h1("60min", HOURS.toSeconds(1)),
  h4("4hour", HOURS.toSeconds(4)),
  h6("6hour", HOURS.toSeconds(6)),
  h12("12hour", HOURS.toSeconds(12)),

  d1("1day", DAYS.toSeconds(1)),
  w1("1week", DAYS.toSeconds(7)),
  M1("1mon", DAYS.toSeconds(30)),
  Y1("1year", DAYS.toSeconds(365));

  private final String code;
  private final Long seconds;

  private KlineInterval(String code, Long seconds) {
    this.seconds = seconds;
    this.code = code;
  }

  public Long getSeconds() {
    return seconds;
  }

  public String code() {
    return code;
  }
}
