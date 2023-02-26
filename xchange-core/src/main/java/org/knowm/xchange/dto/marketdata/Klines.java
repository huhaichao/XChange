package org.knowm.xchange.dto.marketdata;

import org.knowm.xchange.ExchangeType;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.instrument.Instrument;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public final class Klines implements Serializable {

    private final ExchangeType exchangeType;

    private final List<Kline> klines ;

    private final Instrument instrument;

    private final KlineInterval interval;

    private final Integer limit;

    private final Date startTime ;

    private final Date endTime ;

    public Klines(List<Kline> klines, Instrument instrument, KlineInterval interval, Integer limit) {
        this.exchangeType = null;
        this.klines = klines;
        this.instrument = instrument;
        this.interval = interval;
        this.limit = limit;
        this.startTime = null;
        this.endTime = null ;
    }
    public Klines(ExchangeType exchangeType, List<Kline> klines, Instrument instrument, KlineInterval interval, Integer limit) {
        this.exchangeType = exchangeType;
        this.klines = klines;
        this.instrument = instrument;
        this.interval = interval;
        this.limit = limit;
        this.startTime = null;
        this.endTime = null ;
    }

    public Klines(List<Kline> klines, Instrument instrument, KlineInterval interval, Integer limit, Date startTime, Date endTime) {
        this.exchangeType = null;
        this.klines = klines;
        this.instrument = instrument;
        this.interval = interval;
        this.limit = limit;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Klines(ExchangeType exchangeType, List<Kline> klines, Instrument instrument, KlineInterval interval, Integer limit, Date startTime, Date endTime) {
        this.exchangeType = exchangeType;
        this.klines = klines;
        this.instrument = instrument;
        this.interval = interval;
        this.limit = limit;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public List<Kline> getKlines() {
        return klines;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public CurrencyPair getPair() {
        if (instrument instanceof CurrencyPair){
            return (CurrencyPair)instrument;
        }
        if (instrument instanceof FuturesContract){
            return ((FuturesContract) instrument).getCurrencyPair();
        }
        return null;
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

    public ExchangeType getExchangeType() {
        return exchangeType;
    }
}
