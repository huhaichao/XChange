package org.knowm.xchange.dto.marketdata;

import org.knowm.xchange.currency.CurrencyPair;

import java.io.Serializable;
import java.util.List;

public final class Klines implements Serializable {

    private final List<Kline> klines ;

    private final CurrencyPair pair;

    private final KlineInterval interval;

    private final Integer limit;

    private final Long startTime ;

    private final Long endTime ;

    public Klines(List<Kline> klines, CurrencyPair pair, KlineInterval interval, Integer limit) {
        this.klines = klines;
        this.pair = pair;
        this.interval = interval;
        this.limit = limit;
        this.startTime = null;
        this.endTime = null ;
    }

    public Klines(List<Kline> klines, CurrencyPair pair, KlineInterval interval, Integer limit, Long startTime, Long endTime) {
        this.klines = klines;
        this.pair = pair;
        this.interval = interval;
        this.limit = limit;
        this.startTime = startTime;
        this.endTime = endTime;
    }


    public List<Kline> getKlines() {
        return klines;
    }

    public CurrencyPair getPair() {
        return pair;
    }

    public KlineInterval getInterval() {
        return interval;
    }

    public Integer getLimit() {
        return limit;
    }

    public Long getStartTime() {
        return startTime;
    }

    public Long getEndTime() {
        return endTime;
    }
}
