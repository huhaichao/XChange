import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import info.bitrich.xchangestream.dto.WrapCurrency;
import io.reactivex.disposables.Disposable;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.KlineInterval;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GateioKlineManualExample {
  private static final Logger LOG = LoggerFactory.getLogger(GateioManualExample.class);

  public static void main(String[] args) throws Exception {
    ProductSubscription productSubscription =
        ProductSubscription.create()
            .addKlines(new WrapCurrency(CurrencyPair.BTC_USDT, KlineInterval.m5))
            .build();

    ExchangeSpecification spec =
        StreamingExchangeFactory.INSTANCE
            .createExchangeWithoutSpecification(GateioStreamingExchange.class)
            .getDefaultExchangeSpecification();
    spec.setShouldLoadRemoteMetaData(false);

    GateioStreamingExchange exchange =
        (GateioStreamingExchange) StreamingExchangeFactory.INSTANCE.createExchange(spec);

    exchange.connect(productSubscription).blockingAwait();
    exchange.getStreamingMarketDataService()
            .getKlines(CurrencyPair.BTC_USDT, KlineInterval.m5)
            .subscribe(System.out::println);

    exchange.getStreamingMarketDataService()
            .getKlines(CurrencyPair.ETH_USDT, KlineInterval.m5)
            .subscribe(System.out::println);


    Thread.sleep(Integer.MAX_VALUE);

  }
}
