package info.bitrich.xchangestream.dto;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.KlineInterval;
import org.knowm.xchange.instrument.Instrument;

public class WrapCurrency {

    private final Instrument currencyPair;

    private final String contractType;

    private final KlineInterval klineInterval;

    public WrapCurrency(Instrument currencyPair, KlineInterval klineInterval) {
        this.currencyPair = currencyPair;
        this.klineInterval = klineInterval;
        this.contractType = null;
    }

    public WrapCurrency(CurrencyPair currencyPair, KlineInterval klineInterval, String contractType) {
        this.currencyPair = currencyPair;
        this.klineInterval = klineInterval;
        this.contractType = contractType;
    }

    public KlineInterval getKlineInterval() {
        return klineInterval;
    }

    public Instrument getCurrencyPair() {
        return currencyPair;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        WrapCurrency wrapCurrency = (WrapCurrency) obj;

        if (this.contractType == null){
            return this.currencyPair.equals(wrapCurrency.getCurrencyPair())
                    && this.klineInterval.equals(wrapCurrency.getKlineInterval());
        }
        return this.currencyPair.equals(wrapCurrency.getCurrencyPair())
                && this.klineInterval.equals(wrapCurrency.getKlineInterval())
                && this.contractType.equals(wrapCurrency.getContractType());
    }

    @Override
    public int hashCode() {
        return  currencyPair.hashCode() + klineInterval.getCode().hashCode();
    }

    public String getContractType() {
        return contractType;
    }
}
