package info.bitrich.xchangestream.okex.examples;

import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import info.bitrich.xchangestream.okex.OkexStreamingExchange;
import info.bitrich.xchangestream.okex.OkexStreamingMarketDataService;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.marketdata.KlineInterval;
import org.knowm.xchange.instrument.Instrument;

public class OkexStreamingMarketDataServiceExample {

    private static OkexStreamingMarketDataService okexStreamingMarketDataService;

    public static void main(String[] args) throws InterruptedException {
        setUp();

        //testGetTrades();
        testGetTicker();

        //testGetOrderBook();
        //testGetKline();
        Thread.sleep(5000);

        System.exit(0);
    }

    public static void setUp() {
        StreamingExchange okxExchange = StreamingExchangeFactory.INSTANCE.createExchange(OkexStreamingExchange.class);
        okxExchange.connect().blockingAwait();
        okexStreamingMarketDataService = (OkexStreamingMarketDataService) okxExchange.getStreamingMarketDataService();
    }

    public static void testGetTrades() throws InterruptedException {
        Instrument instrument = CurrencyPair.BTC_USDT;
        okexStreamingMarketDataService.getTrades(instrument).forEach(System.out::println);
        Thread.sleep(3000);
    }

    public static  void testGetTicker() throws InterruptedException {
        Instrument instrument = new FuturesContract("BTC/USD/221230");
        okexStreamingMarketDataService.getTicker(instrument).forEach(System.out::println);
        Thread.sleep(3000);
    }

    public static  void testGetKline() throws InterruptedException {
        Instrument instrument = new FuturesContract("BTC/USD/221230");
        okexStreamingMarketDataService.getKlines(CurrencyPair.BTC_USDT, KlineInterval.m1).forEach(System.out::println);
        Thread.sleep(3000);
    }

    public static void testGetOrderBook() throws InterruptedException {
        Instrument instrument = CurrencyPair.BTC_USDT;
        okexStreamingMarketDataService.getOrderBook(instrument, "books5").forEach(System.out::println);
        Thread.sleep(3000);
    }

}
