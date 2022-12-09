package org.knowm.xchange.binance;

import org.knowm.xchange.binance.dto.BinanceException;
import org.knowm.xchange.binance.dto.account.*;
import org.knowm.xchange.binance.dto.marketdata.*;
import org.knowm.xchange.binance.dto.meta.BinanceSystemStatus;
import org.knowm.xchange.binance.dto.meta.BinanceTime;
import org.knowm.xchange.binance.dto.meta.exchangeinfo.BinanceExchangeInfo;
import org.knowm.xchange.binance.dto.trade.*;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
public interface BinanceUsdsFutureAuthenticated extends BinanceAuthenticated {

  String SIGNATURE = "signature";
  String X_MBX_APIKEY = "X-MBX-APIKEY";



  /**
   * Fetch system status which is normal or system maintenance.
   *
   * @throws IOException
   */
  @GET
  @Path("sapi/v1/system/status")
  BinanceSystemStatus systemStatus() throws IOException;

  /**
   * Test connectivity to the Rest API.
   *
   * @return
   * @throws IOException
   */
  @GET
  @Path("/fapi/v1/ping")
  Object ping() throws IOException;

  /**
   * Test connectivity to the Rest API and get the current server time.
   *
   * @return
   * @throws IOException
   */
  @GET
  @Path("/fapi/v1/time")
  BinanceTime time() throws IOException;

  /**
   * Current exchange trading rules and symbol information.
   *
   * @return
   * @throws IOException
   */
  @GET
  @Path("/fapi/v1/exchangeInfo")
  BinanceExchangeInfo exchangeInfo() throws IOException;

  /**
   * @param symbol
   * @param limit optional, default 100 max 5000. Valid limits: [5, 10, 20, 50, 100, 500, 1000,
   *     5000]
   * @return
   * @throws IOException
   * @throws BinanceException
   */
  @GET
  @Path("/fapi/v1/depth")
  BinanceOrderbook depth(@QueryParam("symbol") String symbol, @QueryParam("limit") Integer limit)
          throws IOException, BinanceException;

  /**
   * Get compressed, aggregate trades. Trades that fill at the time, from the same order, with the
   * same price will have the quantity aggregated.<br>
   * If both startTime and endTime are sent, limit should not be sent AND the distance between
   * startTime and endTime must be less than 24 hours.<br>
   * If frondId, startTime, and endTime are not sent, the most recent aggregate trades will be
   * returned.
   *
   * @param symbol
   * @param fromId optional, ID to get aggregate trades from INCLUSIVE.
   * @param limit optional, Default 500; max 500.
   * @return
   * @throws IOException
   * @throws BinanceException
   */
  @GET
  @Path("/fapi/v1/historicalTrades")
  List<BinanceAggTrades> aggTrades(
          @QueryParam("symbol") String symbol,
          @QueryParam("fromId") Long fromId,
          @QueryParam("limit") Integer limit)
          throws IOException, BinanceException;

  /**
   * Kline/candlestick bars for a symbol. Klines are uniquely identified by their open time.<br>
   * If startTime and endTime are not sent, the most recent klines are returned.
   *
   * @param symbol
   * @param interval
   * @param limit optional, default 500; max 500.
   * @param startTime optional
   * @param endTime optional
   * @return
   * @throws IOException
   * @throws BinanceException
   */
  @GET
  @Path("/fapi/v1/klines")
  List<Object[]> klines(
          @QueryParam("symbol") String symbol,
          @QueryParam("interval") String interval,
          @QueryParam("limit") Integer limit,
          @QueryParam("startTime") Long startTime,
          @QueryParam("endTime") Long endTime)
          throws IOException, BinanceException;

  /**
   * 24 hour price change statistics for all symbols. - bee carreful this api call have a big
   * weight, only about 4 call per minut can be without ban.
   *
   * @return
   * @throws IOException
   * @throws BinanceException
   */
  @GET
  @Path("/fapi/v1/ticker/24hr")
  List<BinanceTicker24h> ticker24h() throws IOException, BinanceException;

  /**
   * 24 hour price change statistics.
   *
   * @param symbol
   * @return
   * @throws IOException
   * @throws BinanceException
   */
  @GET
  @Path("/fapi/v1/ticker/24hr")
  BinanceTicker24h ticker24h(@QueryParam("symbol") String symbol)
          throws IOException, BinanceException;

  /**
   * Latest price for a symbol.
   *
   * @return
   * @throws IOException
   * @throws BinanceException
   */
  @GET
  @Path("/fapi/v1/ticker/price")
  BinancePrice tickerPrice(@QueryParam("symbol") String symbol)
          throws IOException, BinanceException;

  /**
   * Latest price for all symbols.
   *
   * @return
   * @throws IOException
   * @throws BinanceException
   */
  @GET
  @Path("/fapi/v1/ticker/price")
  List<BinancePrice> tickerAllPrices() throws IOException, BinanceException;

  /**
   * Best price/qty on the order book for all symbols.
   *
   * @return
   * @throws IOException
   * @throws BinanceException
   */
  @GET
  @Path("/fapi/v1/ticker/bookTicker")
  List<BinancePriceQuantity> tickerAllBookTickers() throws IOException, BinanceException;

  /**
   * Get Funding Rate History
   *
   * @return
   * @throws IOException
   * @throws BinanceException
   */
  @GET
  @Path("/fapi/v1/fundingRate")
  List<BinanceFundingRate> fundingRate(@QueryParam("symbol") String symbol,
                                       @QueryParam("limit") Integer limit,
                                       @QueryParam("startTime") Long startTime,
                                       @QueryParam("endTime") Long endTime) throws IOException, BinanceException;


  /**
   * Long/Short Ratio
   *
   * @return
   * @throws IOException
   * @throws BinanceException
   */
  @GET
  @Path("/futures/data/globalLongShortAccountRatio")
  List<BinanceLongShortRatio> longShortAccountRatio(@QueryParam("symbol") String symbol,
                                                    @QueryParam("interval") String interval,
                                                    @QueryParam("limit") Integer limit,
                                                    @QueryParam("startTime") Long startTime,
                                                    @QueryParam("endTime") Long endTime) throws IOException, BinanceException;



  /**
   * Top Trader Long/Short Ratio (Accounts)
   *
   * @return
   * @throws IOException
   * @throws BinanceException
   */
  @GET
  @Path("/futures/data/topLongShortAccountRatio")
  List<BinanceLongShortRatio> topLongShortAccountRatio(@QueryParam("symbol") String symbol,
                                                       @QueryParam("interval") String interval,
                                                       @QueryParam("limit") Integer limit,
                                                       @QueryParam("startTime") Long startTime,
                                                       @QueryParam("endTime") Long endTime) throws IOException, BinanceException;


  /**
   * Top Trader Long/Short Ratio (Positions)
   *
   * @return
   * @throws IOException
   * @throws BinanceException
   */
  @GET
  @Path("/futures/data/topLongShortPositionRatio")
  List<BinanceLongShortRatio> topLongShortPositionRatio(@QueryParam("symbol") String symbol,
                                                        @QueryParam("interval") String interval,
                                                        @QueryParam("limit") Integer limit,
                                                        @QueryParam("startTime") Long startTime,
                                                        @QueryParam("endTime") Long endTime) throws IOException, BinanceException;

  /**
   * Send in a new order
   *
   * @param symbol
   * @param side
   * @param type
   * @param timeInForce
   * @param quantity
   * @param quoteOrderQty optional
   * @param price optional, must be provided for limit orders only
   * @param newClientOrderId optional, a unique id for the order. Automatically generated if not
   *     sent.
   * @param stopPrice optional, used with stop orders
   * @param trailingDelta optional, used with STOP_LOSS, STOP_LOSS_LIMIT, TAKE_PROFIT, and
   *     TAKE_PROFIT_LIMIT orders
   * @param icebergQty optional, used with iceberg orders
   * @param newOrderRespType optional, MARKET and LIMIT order types default to FULL, all other
   *     orders default to ACK
   * @param recvWindow optional
   * @param timestamp
   * @return
   * @throws IOException
   * @throws BinanceException
   * @see <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">New order - Spot
   *     API docs -</a>
   */
  @POST
  @Path("/fapi/v1/order")
  BinanceNewOrder newOrder(
      @FormParam("symbol") String symbol,
      @FormParam("side") OrderSide side,
      @FormParam("type") OrderType type,
      @FormParam("timeInForce") TimeInForce timeInForce,
      @FormParam("quantity") BigDecimal quantity,
      @FormParam("quoteOrderQty") BigDecimal quoteOrderQty,
      @FormParam("price") BigDecimal price,
      @FormParam("newClientOrderId") String newClientOrderId,
      @FormParam("stopPrice") BigDecimal stopPrice,
      @FormParam("trailingDelta") Long trailingDelta,
      @FormParam("icebergQty") BigDecimal icebergQty,
      @FormParam("newOrderRespType") BinanceNewOrder.NewOrderResponseType newOrderRespType,
      @FormParam("recvWindow") Long recvWindow,
      @FormParam("timestamp") SynchronizedValueFactory<Long> timestamp,
      @HeaderParam(X_MBX_APIKEY) String apiKey,
      @QueryParam(SIGNATURE) ParamsDigest signature)
      throws IOException, BinanceException;

  /**
   * Test new order creation and signature/recvWindow long. Creates and validates a new order but
   * does not send it into the matching engine.
   *
   * @param symbol
   * @param side
   * @param type
   * @param timeInForce
   * @param quantity
   * @param quoteOrderQty optional
   * @param price
   * @param newClientOrderId optional, a unique id for the order. Automatically generated by
   *     default.
   * @param stopPrice optional, used with STOP orders
   * @param trailingDelta optional, used with STOP_LOSS, STOP_LOSS_LIMIT, TAKE_PROFIT, and
   *     TAKE_PROFIT_LIMIT orders
   * @param icebergQty optional used with icebergOrders
   * @param recvWindow optional
   * @param timestamp
   * @return
   * @throws IOException
   * @throws BinanceException
   * @see <a href="https://binance-docs.github.io/apidocs/spot/en/#test-new-order-trade">Test new
   *     order - Spot API dsocs -</a>
   */
  @POST
  @Path("/fapi/v1/order/test")
  Object testNewOrder(
      @FormParam("symbol") String symbol,
      @FormParam("side") OrderSide side,
      @FormParam("type") OrderType type,
      @FormParam("timeInForce") TimeInForce timeInForce,
      @FormParam("quantity") BigDecimal quantity,
      @FormParam("quoteOrderQty") BigDecimal quoteOrderQty,
      @FormParam("price") BigDecimal price,
      @FormParam("newClientOrderId") String newClientOrderId,
      @FormParam("stopPrice") BigDecimal stopPrice,
      @FormParam("trailingDelta") Long trailingDelta,
      @FormParam("icebergQty") BigDecimal icebergQty,
      @FormParam("recvWindow") Long recvWindow,
      @FormParam("timestamp") SynchronizedValueFactory<Long> timestamp,
      @HeaderParam(X_MBX_APIKEY) String apiKey,
      @QueryParam(SIGNATURE) ParamsDigest signature)
      throws IOException, BinanceException;

  /**
   * Check an order's status.<br>
   * Either orderId or origClientOrderId must be sent.
   *
   * @param symbol
   * @param orderId optional
   * @param origClientOrderId optional
   * @param recvWindow optional
   * @param timestamp
   * @param apiKey
   * @param signature
   * @return
   * @throws IOException
   * @throws BinanceException
   */
  @GET
  @Path("/fapi/v1/order")
  BinanceOrder orderStatus(
      @QueryParam("symbol") String symbol,
      @QueryParam("orderId") long orderId,
      @QueryParam("origClientOrderId") String origClientOrderId,
      @QueryParam("recvWindow") Long recvWindow,
      @QueryParam("timestamp") SynchronizedValueFactory<Long> timestamp,
      @HeaderParam(X_MBX_APIKEY) String apiKey,
      @QueryParam(SIGNATURE) ParamsDigest signature)
      throws IOException, BinanceException;

  /**
   * Cancel an active order.
   *
   * @param symbol
   * @param orderId optional
   * @param origClientOrderId optional
   * @param newClientOrderId optional, used to uniquely identify this cancel. Automatically
   *     generated by default.
   * @param recvWindow optional
   * @param timestamp
   * @param apiKey
   * @param signature
   * @return
   * @throws IOException
   * @throws BinanceException
   */
  @DELETE
  @Path("/fapi/v1/order")
  BinanceCancelledOrder cancelOrder(
      @QueryParam("symbol") String symbol,
      @QueryParam("orderId") long orderId,
      @QueryParam("origClientOrderId") String origClientOrderId,
      @QueryParam("newClientOrderId") String newClientOrderId,
      @QueryParam("recvWindow") Long recvWindow,
      @QueryParam("timestamp") SynchronizedValueFactory<Long> timestamp,
      @HeaderParam(X_MBX_APIKEY) String apiKey,
      @QueryParam(SIGNATURE) ParamsDigest signature)
      throws IOException, BinanceException;

  /**
   * Cancels all active orders on a symbol. This includes OCO orders.
   *
   * @param symbol
   * @param recvWindow optional
   * @param timestamp
   * @return
   * @throws IOException
   * @throws BinanceException
   */
  @DELETE
  @Path("/fapi/v1/openOrders")
  List<BinanceCancelledOrder> cancelAllOpenOrders(
      @QueryParam("symbol") String symbol,
      @QueryParam("recvWindow") Long recvWindow,
      @QueryParam("timestamp") SynchronizedValueFactory<Long> timestamp,
      @HeaderParam(X_MBX_APIKEY) String apiKey,
      @QueryParam(SIGNATURE) ParamsDigest signature)
      throws IOException, BinanceException;

  /**
   * Get open orders on a symbol.
   *
   * @param symbol optional
   * @param recvWindow optional
   * @param timestamp
   * @return
   * @throws IOException
   * @throws BinanceException
   */
  @GET
  @Path("/fapi/v1/openOrders")
  List<BinanceOrder> openOrders(
      @QueryParam("symbol") String symbol,
      @QueryParam("recvWindow") Long recvWindow,
      @QueryParam("timestamp") SynchronizedValueFactory<Long> timestamp,
      @HeaderParam(X_MBX_APIKEY) String apiKey,
      @QueryParam(SIGNATURE) ParamsDigest signature)
      throws IOException, BinanceException;

  /**
   * Get all account orders; active, canceled, or filled. <br>
   * If orderId is set, it will get orders >= that orderId. Otherwise most recent orders are
   * returned.
   *
   * @param symbol
   * @param orderId optional
   * @param limit optional
   * @param recvWindow optional
   * @param timestamp
   * @param apiKey
   * @param signature
   * @return
   * @throws IOException
   * @throws BinanceException
   */
  @GET
  @Path("/fapi/v1/allOrders")
  List<BinanceOrder> allOrders(
      @QueryParam("symbol") String symbol,
      @QueryParam("orderId") Long orderId,
      @QueryParam("limit") Integer limit,
      @QueryParam("recvWindow") Long recvWindow,
      @QueryParam("timestamp") SynchronizedValueFactory<Long> timestamp,
      @HeaderParam(X_MBX_APIKEY) String apiKey,
      @QueryParam(SIGNATURE) ParamsDigest signature)
      throws IOException, BinanceException;

  /**
   * Get current account information.
   *
   * @param recvWindow optional
   * @param timestamp
   * @return
   * @throws IOException
   * @throws BinanceException
   */
  @GET
  @Path("/fapi/v1/account")
  BinanceAccountInformation account(
      @QueryParam("recvWindow") Long recvWindow,
      @QueryParam("timestamp") SynchronizedValueFactory<Long> timestamp,
      @HeaderParam(X_MBX_APIKEY) String apiKey,
      @QueryParam(SIGNATURE) ParamsDigest signature)
      throws IOException, BinanceException;

  /**
   * Get trades for a specific account and symbol.
   *
   * @param symbol
   * @param orderId optional
   * @param startTime optional
   * @param endTime optional
   * @param fromId optional, tradeId to fetch from. Default gets most recent trades.
   * @param limit optional, default 500; max 1000.
   * @param recvWindow optional
   * @param timestamp
   * @param apiKey
   * @param signature
   * @return
   * @throws IOException
   * @throws BinanceException
   */
  @GET
  @Path("/fapi/v1/myTrades")
  List<BinanceTrade> myTrades(
      @QueryParam("symbol") String symbol,
      @QueryParam("orderId") Long orderId,
      @QueryParam("startTime") Long startTime,
      @QueryParam("endTime") Long endTime,
      @QueryParam("fromId") Long fromId,
      @QueryParam("limit") Integer limit,
      @QueryParam("recvWindow") Long recvWindow,
      @QueryParam("timestamp") SynchronizedValueFactory<Long> timestamp,
      @HeaderParam(X_MBX_APIKEY) String apiKey,
      @QueryParam(SIGNATURE) ParamsDigest signature)
      throws IOException, BinanceException;

  /**
   * Retrieves the dust log from Binance. If you have many currencies with low amount (=dust) that
   * cannot be traded, because their amount is less than the minimum amount required for trading
   * them, you can convert all these currencies at once into BNB with the button "Convert Small
   * Balance to BNB".
   *
   * @param startTime optional. If set, also the endTime must be set. If neither time is set, the
   *     100 most recent dust logs are returned.
   * @param endTime optional. If set, also the startTime must be set. If neither time is set, the
   *     100 most recent dust logs are returned.
   * @param recvWindow optional
   * @param timestamp mandatory
   * @return
   * @throws IOException
   * @throws BinanceException
   */
  @GET
  @Path("/sapi/v1/asset/dribblet")
  BinanceDustLog getDustLog(
      @QueryParam("startTime") Long startTime,
      @QueryParam("endTime") Long endTime,
      @QueryParam("recvWindow") Long recvWindow,
      @QueryParam("timestamp") SynchronizedValueFactory<Long> timestamp,
      @HeaderParam(X_MBX_APIKEY) String apiKey,
      @QueryParam(SIGNATURE) ParamsDigest signature)
      throws IOException, BinanceException;

  /**
   * Submit a withdraw request.
   *
   * @param coin
   * @param address
   * @param addressTag optional for Ripple
   * @param amount
   * @param name optional, description of the address
   * @param recvWindow optional
   * @param timestamp
   * @param apiKey
   * @param signature
   * @return
   * @throws IOException
   * @throws BinanceException
   */
  @POST
  @Path("/sapi/v1/capital/withdraw/apply")
  WithdrawResponse withdraw(
      @FormParam("coin") String coin,
      @FormParam("address") String address,
      @FormParam("addressTag") String addressTag,
      @FormParam("amount") BigDecimal amount,
      @FormParam("name") String name,
      @FormParam("recvWindow") Long recvWindow,
      @FormParam("timestamp") SynchronizedValueFactory<Long> timestamp,
      @HeaderParam(X_MBX_APIKEY) String apiKey,
      @QueryParam(SIGNATURE) ParamsDigest signature)
      throws IOException, BinanceException;

  /**
   * Fetch deposit history.
   *
   * @param coin optional
   * @param startTime optional
   * @param endTime optional
   * @param recvWindow optional
   * @param timestamp
   * @param apiKey
   * @param signature
   * @return
   * @throws IOException
   * @throws BinanceException
   */
  @GET
  @Path("/sapi/v1/capital/deposit/hisrec")
  List<BinanceDeposit> depositHistory(
      @QueryParam("coin") String coin,
      @QueryParam("startTime") Long startTime,
      @QueryParam("endTime") Long endTime,
      @QueryParam("recvWindow") Long recvWindow,
      @QueryParam("timestamp") SynchronizedValueFactory<Long> timestamp,
      @HeaderParam(X_MBX_APIKEY) String apiKey,
      @QueryParam(SIGNATURE) ParamsDigest signature)
      throws IOException, BinanceException;

  /**
   * Fetch withdraw history.
   *
   * @param coin optional
   * @param startTime optional
   * @param endTime optional
   * @param recvWindow optional
   * @param timestamp
   * @param apiKey
   * @param signature
   * @return
   * @throws IOException
   * @throws BinanceException
   */
  @GET
  @Path("/sapi/v1/capital/withdraw/history")
  List<BinanceWithdraw> withdrawHistory(
      @QueryParam("coin") String coin,
      @QueryParam("startTime") Long startTime,
      @QueryParam("endTime") Long endTime,
      @QueryParam("recvWindow") Long recvWindow,
      @QueryParam("timestamp") SynchronizedValueFactory<Long> timestamp,
      @HeaderParam(X_MBX_APIKEY) String apiKey,
      @QueryParam(SIGNATURE) ParamsDigest signature)
      throws IOException, BinanceException;

  /**
   * Fetch small amounts of assets exchanged BNB records.
   *
   * @param asset optional
   * @param startTime optional
   * @param endTime optional
   * @param recvWindow optional
   * @param timestamp
   * @param apiKey
   * @param signature
   * @return
   * @throws IOException
   * @throws BinanceException
   */
  @GET
  @Path("/sapi/v1/asset/assetDividend")
  AssetDividendResponse assetDividend(
      @QueryParam("asset") String asset,
      @QueryParam("startTime") Long startTime,
      @QueryParam("endTime") Long endTime,
      @QueryParam("recvWindow") Long recvWindow,
      @QueryParam("timestamp") SynchronizedValueFactory<Long> timestamp,
      @HeaderParam(X_MBX_APIKEY) String apiKey,
      @QueryParam(SIGNATURE) ParamsDigest signature)
      throws IOException, BinanceException;

  @GET
  @Path("/sapi/v1/sub-account/sub/transfer/history")
  List<TransferHistory> transferHistory(
      @QueryParam("fromEmail") String fromEmail,
      @QueryParam("startTime") Long startTime,
      @QueryParam("endTime") Long endTime,
      @QueryParam("page") Integer page,
      @QueryParam("limit") Integer limit,
      @QueryParam("recvWindow") Long recvWindow,
      @QueryParam("timestamp") SynchronizedValueFactory<Long> timestamp,
      @HeaderParam(X_MBX_APIKEY) String apiKey,
      @QueryParam(SIGNATURE) ParamsDigest signature)
      throws IOException, BinanceException;

  @GET
  @Path("/sapi/v1/sub-account/transfer/subUserHistory")
  List<TransferSubUserHistory> transferSubUserHistory(
      @QueryParam("asset") String asset,
      @QueryParam("type") Integer type,
      @QueryParam("startTime") Long startTime,
      @QueryParam("endTime") Long endTime,
      @QueryParam("limit") Integer limit,
      @QueryParam("recvWindow") Long recvWindow,
      @QueryParam("timestamp") SynchronizedValueFactory<Long> timestamp,
      @HeaderParam(X_MBX_APIKEY) String apiKey,
      @QueryParam(SIGNATURE) ParamsDigest signature)
      throws IOException, BinanceException;

  /**
   * Fetch deposit address.
   *
   * @param coin
   * @param recvWindow
   * @param timestamp
   * @param apiKey
   * @param signature
   * @return
   * @throws IOException
   * @throws BinanceException
   */
  @GET
  @Path("/sapi/v1/capital/deposit/address")
  DepositAddress depositAddress(
      @QueryParam("coin") String coin,
      @QueryParam("recvWindow") Long recvWindow,
      @QueryParam("timestamp") SynchronizedValueFactory<Long> timestamp,
      @HeaderParam(X_MBX_APIKEY) String apiKey,
      @QueryParam(SIGNATURE) ParamsDigest signature)
      throws IOException, BinanceException;

  /**
   * Fetch asset details.
   *
   * @param recvWindow
   * @param timestamp
   * @param apiKey
   * @param signature
   * @return
   * @throws IOException
   * @throws BinanceException
   */
  @GET
  @Path("/sapi/v1/asset/assetDetail")
  Map<String, AssetDetail> assetDetail(
      @QueryParam("recvWindow") Long recvWindow,
      @QueryParam("timestamp") SynchronizedValueFactory<Long> timestamp,
      @HeaderParam(X_MBX_APIKEY) String apiKey,
      @QueryParam(SIGNATURE) ParamsDigest signature)
      throws IOException, BinanceException;

  /**
   * Returns a listen key for websocket login.
   *
   * @param apiKey the api key
   * @return
   * @throws BinanceException
   * @throws IOException
   */
  @POST
  @Path("/api/v3/userDataStream")
  BinanceListenKey startUserDataStream(@HeaderParam(X_MBX_APIKEY) String apiKey)
      throws IOException, BinanceException;

  /**
   * Keeps the authenticated websocket session alive.
   *
   * @param apiKey the api key
   * @param listenKey the api secret
   * @return
   * @throws BinanceException
   * @throws IOException
   */
  @PUT
  @Path("/fapi/v1/userDataStream?listenKey={listenKey}")
  Map<?, ?> keepAliveUserDataStream(
      @HeaderParam(X_MBX_APIKEY) String apiKey, @PathParam("listenKey") String listenKey)
      throws IOException, BinanceException;

  /**
   * Closes the websocket authenticated connection.
   *
   * @param apiKey the api key
   * @param listenKey the api secret
   * @return
   * @throws BinanceException
   * @throws IOException
   */
  @DELETE
  @Path("/fapi/v1/userDataStream?listenKey={listenKey}")
  Map<?, ?> closeUserDataStream(
      @HeaderParam(X_MBX_APIKEY) String apiKey, @PathParam("listenKey") String listenKey)
      throws IOException, BinanceException;
}
