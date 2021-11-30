package dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Kline;
import org.knowm.xchange.utils.DateUtils;
import java.math.BigDecimal;

@Getter
@Setter
/** Author: Max Gao (gaamox@tutanota.com) Created: 05-05-2021 */
public class GateioKlineResponse extends GateioWebSocketTransaction {
  @JsonProperty("result")
  private Result result;

  @Override
  public CurrencyPair getCurrencyPair() {
    String[] names =result.getName().split("_");
    return new CurrencyPair(new StringBuilder().append(names[1]).append("/").append(names[2]).toString());
  }

  @Getter
  @Setter
  public class Result {

    @JsonProperty("t")
    private long openTime;

    @JsonProperty("o")
    private BigDecimal open;

    @JsonProperty("c")
    private  BigDecimal close;

    @JsonProperty("l")
    private  BigDecimal low;

    @JsonProperty("h")
    private  BigDecimal high;

    @JsonProperty("v")
    private  BigDecimal vol;

    @JsonProperty("n")
    private  String name;
  }

  public Kline toKline(CurrencyPair currencyPair) {
    return new Kline.Builder()
            .id(result.getOpenTime())
            .openTime( DateUtils.fromUnixTime(result.getOpenTime()))
            .open(result.getOpen())
            .close(result.getClose())
            .high(result.getHigh())
            .low(result.getLow())
            .vol(result.getVol())
            .build();
  }
}
