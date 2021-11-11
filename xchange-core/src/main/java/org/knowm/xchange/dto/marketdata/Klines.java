package org.knowm.xchange.dto.marketdata;

import org.knowm.xchange.ExchangeType;
import org.knowm.xchange.currency.CurrencyPair;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public final class Klines implements Serializable {

    private final ExchangeType exchangeType;

    private final List<Kline> klines ;

    private final CurrencyPair pair;

    private final KlineInterval interval;

    private final Integer limit;

    private final Date startTime ;

    private final Date endTime ;

    public Klines(List<Kline> klines, CurrencyPair pair, KlineInterval interval, Integer limit) {
        this.exchangeType = null;
        this.klines = klines;
        this.pair = pair;
        this.interval = interval;
        this.limit = limit;
        this.startTime = null;
        this.endTime = null ;
    }
    public Klines(ExchangeType exchangeType, List<Kline> klines, CurrencyPair pair, KlineInterval interval, Integer limit) {
        this.exchangeType = exchangeType;
        this.klines = klines;
        this.pair = pair;
        this.interval = interval;
        this.limit = limit;
        this.startTime = null;
        this.endTime = null ;
    }

    public Klines(List<Kline> klines, CurrencyPair pair, KlineInterval interval, Integer limit, Date startTime, Date endTime) {
        this.exchangeType = null;
        this.klines = klines;
        this.pair = pair;
        this.interval = interval;
        this.limit = limit;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Klines(ExchangeType exchangeType, List<Kline> klines, CurrencyPair pair, KlineInterval interval, Integer limit, Date startTime, Date endTime) {
        this.exchangeType = exchangeType;
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

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }
}
