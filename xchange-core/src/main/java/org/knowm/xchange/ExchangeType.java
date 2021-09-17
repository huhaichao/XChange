package org.knowm.xchange;

import java.util.Arrays;

public enum ExchangeType {

    HUOBI("HUOBI",1),
    HUOBISTREAM("HUOBISTREAM",2),
    BINANCE("BINANCE",3),
    BINANCESTREAM("BINANCESTREAM",4),
    OKEX("OKEX",5),
    OKEXSTREAM("OKEXSTREAM",6),
    OKCOIN("OKCOIN",7),
    OKCOINSTREAM("OKCOINSTREAM",8);

    ExchangeType(String exchangeName, Integer exchangeType) {
        this.exchangeName = exchangeName;
        this.exchangeType = exchangeType;
    }

    private String exchangeName;

    private Integer exchangeType;


    public String getExchangeName() {
        return exchangeName;
    }

    public Integer getExchangeType() {
        return exchangeType;
    }

    public static ExchangeType exchangeTypeFromType(Integer type){
        return Arrays.stream(ExchangeType.values())
                .filter(exchangeType -> exchangeType.getExchangeType() == type)
                .findFirst()
                .get();
    }

    public static ExchangeType exchangeTypeFromName(String exchangeName){
        return Arrays.stream(ExchangeType.values())
                .filter(exchangeType -> exchangeType.getExchangeName().equals(exchangeName))
                .findFirst()
                .get();
    }
}
