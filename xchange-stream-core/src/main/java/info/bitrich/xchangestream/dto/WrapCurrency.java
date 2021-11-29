package info.bitrich.xchangestream.dto;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.KlineInterval;

public class WrapCurrency {

    private final CurrencyPair currencyPair;

    private final KlineInterval klineInterval;

    public WrapCurrency(CurrencyPair currencyPair, KlineInterval klineInterval) {
        this.currencyPair = currencyPair;
        this.klineInterval = klineInterval;
    }


    public KlineInterval getKlineInterval() {
        return klineInterval;
    }

    public CurrencyPair getCurrencyPair() {
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
        return this.currencyPair.equals(wrapCurrency.getCurrencyPair())
                && this.klineInterval.equals(wrapCurrency.getKlineInterval());
    }

    @Override
    public int hashCode() {
        return  currencyPair.hashCode() + klineInterval.getCode().hashCode();
    }
}
