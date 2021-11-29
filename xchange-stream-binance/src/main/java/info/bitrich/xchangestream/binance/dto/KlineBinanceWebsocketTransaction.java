package info.bitrich.xchangestream.binance.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.binance.dto.marketdata.BinanceKline;
import org.knowm.xchange.dto.marketdata.KlineInterval;

import java.math.BigDecimal;

public class KlineBinanceWebsocketTransaction extends ProductBinanceWebSocketTransaction{

    private final BinanceKline kline;

   /* {
        "e": "kline",     // 事件类型
            "E": 123456789,   // 事件时间
            "s": "BNBBTC",    // 交易对
            "k": {
                "t": 123400000, // 这根K线的起始时间
                "T": 123460000, // 这根K线的结束时间
                "s": "BNBBTC",  // 交易对
                "i": "1m",      // K线间隔
                "f": 100,       // 这根K线期间第一笔成交ID
                "L": 200,       // 这根K线期间末一笔成交ID
                "o": "0.0010",  // 这根K线期间第一笔成交价
                "c": "0.0020",  // 这根K线期间末一笔成交价
                "h": "0.0025",  // 这根K线期间最高成交价
                "l": "0.0015",  // 这根K线期间最低成交价
                "v": "1000",    // 这根K线期间成交量
                "n": 100,       // 这根K线期间成交笔数
                "x": false,     // 这根K线是否完结(是否已经开始下一根K线)
                "q": "1.0000",  // 这根K线期间成交额
                "V": "500",     // 主动买入的成交量
                "Q": "0.500",   // 主动买入的成交额
                "B": "123456"   // 忽略此参数
            }
        }
       */
    public KlineBinanceWebsocketTransaction(
            @JsonProperty("e") String eventType,
            @JsonProperty("E") String eventTime,
            @JsonProperty("s") String symbol,
            @JsonProperty("k") K k
            ) {

        super(eventType, eventTime, symbol);

        kline = new BinanceKline(
                currencyPair,
                KlineInterval.parseFromCode(k.getInterval()),
                k.getOpenTime(),
                k.getOpenTime(),
                k.getFirstPrice(),
                k.getHighPrice(),
                k.getLowPrice(),
                k.getLastPrice(),
                k.getVolume(),
                k.getEndTime(),
                k.getQuoteVolume(),
                k.getCount(),
                k.getVolume(),
                k.getByVolume()
        );
    }

   static class  K {
        @JsonProperty("t") Long openTime;
        @JsonProperty("T") Long endTime;
        @JsonProperty("i") String interval;
        @JsonProperty("f") Long firstId;
        @JsonProperty("L") Long lastId;
        @JsonProperty("o") BigDecimal firstPrice;
        @JsonProperty("c") BigDecimal lastPrice;
        @JsonProperty("h") BigDecimal highPrice;
        @JsonProperty("l") BigDecimal lowPrice;
        @JsonProperty("v") BigDecimal volume;
        @JsonProperty("n") Long count;
        @JsonProperty("x") Boolean isOver;
        @JsonProperty("q") BigDecimal quoteVolume;
        @JsonProperty("V") BigDecimal byCount;
        @JsonProperty("Q") BigDecimal byVolume;

        public K(
            @JsonProperty("t") Long openTime,
            @JsonProperty("T") Long endTime,
            @JsonProperty("i") String interval,
            @JsonProperty("f") Long firstId,
            @JsonProperty("L") Long lastId,
            @JsonProperty("o") BigDecimal firstPrice,
            @JsonProperty("c") BigDecimal lastPrice,
            @JsonProperty("h") BigDecimal highPrice,
            @JsonProperty("l") BigDecimal lowPrice,
            @JsonProperty("v") BigDecimal volume,
            @JsonProperty("n") Long count,
            @JsonProperty("x") Boolean isOver,
            @JsonProperty("q") BigDecimal quoteVolume,
            @JsonProperty("V") BigDecimal byCount,
            @JsonProperty("Q") BigDecimal byVolume
        ) {
            this.openTime = openTime;
            this.endTime = endTime;
            this.interval = interval;
            this.firstId = firstId;
            this.lastId = lastId;
            this.firstPrice = firstPrice;
            this.lastPrice = lastPrice;
            this.highPrice = highPrice;
            this.lowPrice = lowPrice;
            this.volume = volume;
            this.count = count;
            this.isOver = isOver;
            this.quoteVolume = quoteVolume;
            this.byCount = byCount;
            this.byVolume = byVolume;
        }


        public Long getOpenTime() {
            return openTime;
        }

        public void setOpenTime(Long openTime) {
            this.openTime = openTime;
        }

        public Long getEndTime() {
            return endTime;
        }

        public void setEndTime(Long endTime) {
            this.endTime = endTime;
        }

        public String getInterval() {
            return interval;
        }

        public void setInterval(String interval) {
            this.interval = interval;
        }

        public Long getFirstId() {
            return firstId;
        }

        public void setFirstId(Long firstId) {
            this.firstId = firstId;
        }

        public Long getLastId() {
            return lastId;
        }

        public void setLastId(Long lastId) {
            this.lastId = lastId;
        }

        public BigDecimal getFirstPrice() {
            return firstPrice;
        }

        public void setFirstPrice(BigDecimal firstPrice) {
            this.firstPrice = firstPrice;
        }

        public BigDecimal getLastPrice() {
            return lastPrice;
        }

        public void setLastPrice(BigDecimal lastPrice) {
            this.lastPrice = lastPrice;
        }

        public BigDecimal getHighPrice() {
            return highPrice;
        }

        public void setHighPrice(BigDecimal highPrice) {
            this.highPrice = highPrice;
        }

        public BigDecimal getLowPrice() {
            return lowPrice;
        }

        public void setLowPrice(BigDecimal lowPrice) {
            this.lowPrice = lowPrice;
        }

        public BigDecimal getVolume() {
            return volume;
        }

        public void setVolume(BigDecimal volume) {
            this.volume = volume;
        }

        public Long getCount() {
            return count;
        }

        public void setCount(Long count) {
            this.count = count;
        }

        public Boolean getOver() {
            return isOver;
        }

        public void setOver(Boolean over) {
            isOver = over;
        }

        public BigDecimal getQuoteVolume() {
            return quoteVolume;
        }

        public void setQuoteVolume(BigDecimal quoteVolume) {
            this.quoteVolume = quoteVolume;
        }

        public BigDecimal getByCount() {
            return byCount;
        }

        public void setByCount(BigDecimal byCount) {
            this.byCount = byCount;
        }

        public BigDecimal getByVolume() {
            return byVolume;
        }

        public void setByVolume(BigDecimal byVolume) {
            this.byVolume = byVolume;
        }
    }

    public BinanceKline getKline() {
        return kline;
    }
}
