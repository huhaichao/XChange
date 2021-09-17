package org.knowm.xchange.dto.marketdata;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.io.Serializable;
import java.math.BigDecimal;

@JsonDeserialize(builder = Kline.Builder.class)
public final class Kline implements Serializable {

    private final long id;
    private final long count;
    private final long openTime;
    private final BigDecimal open;
    private final BigDecimal close;
    private final BigDecimal low;
    private final BigDecimal high;
    private final BigDecimal amount;
    private final BigDecimal vol;
    private final long closeTime;

    public Kline(long id, long count, long openTime, BigDecimal open, BigDecimal close, BigDecimal low, BigDecimal high, BigDecimal amount, BigDecimal vol, long closeTime) {
        this.id = id;
        this.count = count;
        this.openTime = openTime;
        this.open = open;
        this.close = close;
        this.low = low;
        this.high = high;
        this.amount = amount;
        this.vol = vol;
        this.closeTime = closeTime;
    }

    public long getId() {
        return id;
    }

    public long getCount() {
        return count;
    }

    public BigDecimal getOpen() {
        return open;
    }

    public BigDecimal getClose() {
        return close;
    }

    public BigDecimal getLow() {
        return low;
    }

    public BigDecimal getHigh() {
        return high;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getVol() {
        return vol;
    }

    public long getOpenTime() {
        return openTime;
    }

    public long getCloseTime() {
        return closeTime;
    }

    @Override
    public String toString() {
        return "Kline {"+
                "id=" + id +
                ", count=" + count +
                ", openTime=" + openTime +
                ", open=" + open +
                ", close=" + close +
                ", low=" + low +
                ", high=" + high +
                ", amount=" + amount +
                ", vol=" + vol +
                ", closeTime=" + closeTime +
                '}';
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder {
        private  long id;
        private  long count;
        private  long openTime;
        private  BigDecimal open;
        private  BigDecimal close;
        private  BigDecimal low;
        private  BigDecimal high;
        private  BigDecimal amount;
        private  BigDecimal vol;
        private  long closeTime;

        private boolean isBuilt = false;

        public  Kline  build(){
            validateState();
            Kline kline = new Kline(id,count,openTime,open,close,low,high,amount,vol,closeTime);
            isBuilt = true;
            return kline;
        }

        private void validateState() {

            if (isBuilt) {
                throw new IllegalStateException("The entity has been built");
            }
        }

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder count(long count) {
            this.count = count;
            return this;
        }

        public Builder open(BigDecimal open) {
            this.open = open;
            return this;
        }

        public Builder close(BigDecimal close) {
            this.close = close;
            return this;
        }

        public Builder low(BigDecimal low) {
            this.low = low;
            return this;
        }

        public Builder high(BigDecimal high) {
            this.high = high;
            return this;
        }

        public Builder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public Builder vol(BigDecimal vol) {
            this.vol = vol;
            return this;
        }

        public Builder openTime(long openTime) {
            this.openTime = openTime;
            return this;
        }

        public Builder closeTime(long closeTime) {
            this.closeTime = closeTime;
            return this;
        }

    }


}
