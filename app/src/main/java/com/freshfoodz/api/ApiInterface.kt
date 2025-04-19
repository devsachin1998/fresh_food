package com.freshfoodz.api

import com.freshfoodz.model.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiInterface {

    @GET(ApiConstants.STOCK_LIST)
    fun getStockList(): Call<StockListResponse>

    @POST(ApiConstants.LOGIN)
    fun doLogin(@Body req: LoginReq): Call<LoginRes>

    @POST(ApiConstants.SIGN_UP)
    fun doSignUp(@Body req: RegisterUpdateReq): Call<MainBaseRes>

    @POST(ApiConstants.CHANGE_PWD)
    fun doChangePwd(@Body req: ChangePwdReq): Call<MainBaseRes>

    @POST(ApiConstants.PLACE_ORDER)
    fun placeOrder(@Body req: OrderPlaceReq): Call<MainBaseRes>

    @GET(ApiConstants.ORDER_LIST)
    fun orderList(@Query("UserID") UserID: Int): Call<OrderListRes>

    @GET(ApiConstants.NOTIFICATION_LIST)
    fun notificationList(@Query("UserID") UserID: Int): Call<NotificationResponse>

    @GET(ApiConstants.CancelOrder)
    fun cancelOrder(@Query("OrderID") OrderID: Int,@Query("UserID") UserID: Int): Call<MainBaseRes>

    @GET(ApiConstants.START_UP)
    fun getStartUp(): Call<StartUpResponse>

    @GET(ApiConstants.WALLET_HISTORY)
    fun getWallet(@Query("UserID") UserID: Int): Call<WalletHistoryResponse>

    @POST(ApiConstants.ADD_PAYMENT)
    fun addpayment(@Body req: AddPaymentRequest): Call<PaymentResponse>

    @GET(ApiConstants.USER_DETAILS)
    fun getProfile(@Query("UserID") UserID: Int): Call<LoginRes>

    @POST(ApiConstants.ADD_MONEY)
    fun addMoney(@Body req: AddMoneyRequest): Call<MainBaseRes>

    @GET(ApiConstants.DELIVERY_TIME)
    fun getTimeRange(): Call<TimeRangeResponse>

}