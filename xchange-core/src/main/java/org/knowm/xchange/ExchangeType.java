package org.knowm.xchange;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum ExchangeType {

    HUOBI("HUOBI","HUOBI",1),
    HUOBISTREAM("HUOBI","HUOBISTREAM",2),
    BINANCE("BINANCE","BINANCE",3),
    BINANCESTREAM("BINANCE","BINANCESTREAM",4),
    OKEX("OKEX","OKEX",5),
    OKEXSTREAM("OKEX","OKEXSTREAM",6),
    OKCOIN("OKCOIN","OKCOIN",7),
    OKCOINSTREAM("OKCOIN","OKCOINSTREAM",8);

    ExchangeType(String exchangeName, String exchangeChannel, Integer exchangeType) {
        this.exchangeName = exchangeName;
        this.exchangeChannel = exchangeChannel;
        this.exchangeType = exchangeType;
    }

    private String exchangeName;

    private String exchangeChannel;

    private Integer exchangeType;


    public String getExchangeName() {
        return exchangeName;
    }

    public Integer getExchangeType() {
        return exchangeType;
    }

    public String getExchangeChannel() {
        return exchangeChannel;
    }

    public static ExchangeType exchangeTypeFromType(Integer type){
        return Arrays.stream(ExchangeType.values())
                .filter(exchangeType -> exchangeType.getExchangeType() == type)
                .findFirst()
                .get();
    }

    public static ExchangeType exchangeTypeFromChannel(String exchangeChannel){
        return Arrays.stream(ExchangeType.values())
                .filter(exchangeType -> exchangeType.getExchangeChannel().equals(exchangeChannel))
                .findFirst()
                .get();
    }

    public static List<ExchangeType> exchangeTypeFromName(String exchangeName){
        return Arrays.stream(ExchangeType.values())
                .filter(exchangeType -> exchangeType.getExchangeName().equals(exchangeName))
                .collect(Collectors.toList());
    }
}
