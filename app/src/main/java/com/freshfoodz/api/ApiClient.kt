package com.freshfoodz.api

import android.content.Context
import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import com.freshfoodz.R
import com.freshfoodz.helper.Functions
import com.freshfoodz.helper.MyApplication
import com.freshfoodz.helper.PrefUtils
import com.freshfoodz.helper.ProgressUtils
import com.freshfoodz.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiClient(private var context: Context) {

    private var apiInterface = MyApplication.retrofit?.create(ApiInterface::class.java)

    fun getStockList(): MutableLiveData<StockListResponse> {

        val liveData = MutableLiveData<StockListResponse>()
        val stockResponse = StockListResponse()

        if (!Functions.isConnected(context)) {
            stockResponse.response_code = "400"
            stockResponse.message = context.getString(R.string.no_internet_title)

        } else {

            ProgressUtils.showProgress(context)
            apiInterface?.getStockList()?.enqueue(object : Callback<StockListResponse> {
                override fun onResponse(
                    call: Call<StockListResponse>,
                    response: Response<StockListResponse>
                ) {
                    ProgressUtils.hideProgress()
                    when {
                        response.code() == ApiConstants.SUCCESS -> {
                            stockResponse.response_code = response.body()!!.response_code
                            stockResponse.obj = response.body()!!.obj
                            stockResponse.message = response.body()!!.message
                        }
                        response.errorBody() != null && TextUtils.isEmpty(
                            response.errorBody()!!.string()
                        ) -> {
                            stockResponse.response_code = "400"
                            stockResponse.message = response.errorBody()!!.string()
                        }
                        else -> {
                            stockResponse.response_code = "400"
                            stockResponse.message = context.getString(R.string.try_again)
                        }
                    }
                    liveData.value = stockResponse
                }

                override fun onFailure(call: Call<StockListResponse>, t: Throwable) {
                    ProgressUtils.hideProgress()
                    stockResponse.response_code = "400"
                    stockResponse.message = t.message!!
                    liveData.value = stockResponse
                }
            })

        }
        return liveData
    }

    fun doLogin(req: LoginReq): MutableLiveData<LoginRes> {

        val liveData = MutableLiveData<LoginRes>()
        val loginRes = LoginRes()

        if (!Functions.isConnected(context)) {
            loginRes.response_code = "400"
            loginRes.message = context.getString(R.string.no_internet_title)

        } else {

            ProgressUtils.showProgress(context)
            apiInterface?.doLogin(req)?.enqueue(object : Callback<LoginRes> {
                override fun onResponse(
                    call: Call<LoginRes>,
                    response: Response<LoginRes>
                ) {
                    ProgressUtils.hideProgress()
                    when {
                        response.code() == ApiConstants.SUCCESS -> {
                            loginRes.response_code = response.body()!!.response_code
                            if (loginRes.response_code == ApiConstants.SUCCESS.toString()) {
                                loginRes.obj = response.body()!!.obj
                            }
                            loginRes.message = response.body()!!.message
                        }
                        response.errorBody() != null && TextUtils.isEmpty(
                            response.errorBody()!!.string()
                        ) -> {
                            loginRes.response_code = "400"
                            loginRes.message = response.errorBody()!!.string()
                        }
                        else -> {
                            loginRes.response_code = "400"
                            loginRes.message = context.getString(R.string.try_again)
                        }
                    }
                    liveData.value = loginRes
                }

                override fun onFailure(call: Call<LoginRes>, t: Throwable) {
                    ProgressUtils.hideProgress()
                    loginRes.response_code = "400"
                    loginRes.message = t.message!!
                    liveData.value = loginRes
                }
            })

        }
        return liveData

    }

    fun doRegisterUpdate(req: RegisterUpdateReq): MutableLiveData<MainBaseRes> {

        val liveData = MutableLiveData<MainBaseRes>()
        val baseRes = MainBaseRes()

        if (!Functions.isConnected(context)) {
            baseRes.response_code = "400"
            baseRes.message = context.getString(R.string.no_internet_title)

        } else {

            ProgressUtils.showProgress(context)
            apiInterface?.doSignUp(req)?.enqueue(object : Callback<MainBaseRes> {
                override fun onResponse(
                    call: Call<MainBaseRes>,
                    response: Response<MainBaseRes>
                ) {
                    ProgressUtils.hideProgress()
                    when {
                        response.code() == ApiConstants.SUCCESS -> {
                            baseRes.response_code = response.body()!!.response_code
                            if (!TextUtils.isEmpty(response.body()!!.obj)) {
                                baseRes.message = response.body()!!.obj
                            } else {
                                baseRes.message = response.body()!!.message
                            }
                        }
                        response.errorBody() != null && TextUtils.isEmpty(
                            response.errorBody()!!.string()
                        ) -> {
                            baseRes.response_code = "400"
                            baseRes.message = response.errorBody()!!.string()
                        }
                        else -> {
                            baseRes.response_code = "400"
                            baseRes.message = context.getString(R.string.try_again)
                        }
                    }
                    liveData.value = baseRes
                }

                override fun onFailure(call: Call<MainBaseRes>, t: Throwable) {
                    ProgressUtils.hideProgress()
                    baseRes.response_code = "400"
                    baseRes.message = t.message!!
                    liveData.value = baseRes
                }
            })

        }
        return liveData
    }

    fun doChangePwd(req: ChangePwdReq): MutableLiveData<MainBaseRes> {

        val liveData = MutableLiveData<MainBaseRes>()
        val baseRes = MainBaseRes()

        if (!Functions.isConnected(context)) {
            baseRes.response_code = "400"
            baseRes.message = context.getString(R.string.no_internet_title)

        } else {

            ProgressUtils.showProgress(context)
            apiInterface?.doChangePwd(req)?.enqueue(object : Callback<MainBaseRes> {
                override fun onResponse(
                    call: Call<MainBaseRes>,
                    response: Response<MainBaseRes>
                ) {
                    ProgressUtils.hideProgress()
                    when {
                        response.code() == ApiConstants.SUCCESS -> {
                            baseRes.response_code = response.body()!!.response_code
                            if (!TextUtils.isEmpty(response.body()!!.obj)) {
                                baseRes.message = response.body()!!.obj
                            } else {
                                baseRes.message = response.body()!!.message
                            }
                        }
                        response.errorBody() != null && TextUtils.isEmpty(
                            response.errorBody()!!.string()
                        ) -> {
                            baseRes.response_code = "400"
                            baseRes.message = response.errorBody()!!.string()
                        }
                        else -> {
                            baseRes.response_code = "400"
                            baseRes.message = context.getString(R.string.try_again)
                        }
                    }
                    liveData.value = baseRes
                }

                override fun onFailure(call: Call<MainBaseRes>, t: Throwable) {
                    ProgressUtils.hideProgress()
                    baseRes.response_code = "400"
                    baseRes.message = t.message!!
                    liveData.value = baseRes
                }
            })

        }
        return liveData
    }


    fun placeOrder(req: OrderPlaceReq): MutableLiveData<MainBaseRes> {

        val liveData = MutableLiveData<MainBaseRes>()
        val baseRes = MainBaseRes()

        if (!Functions.isConnected(context)) {
            baseRes.response_code = "400"
            baseRes.message = context.getString(R.string.no_internet_title)

        } else {

            ProgressUtils.showProgress(context)
            apiInterface?.placeOrder(req)?.enqueue(object : Callback<MainBaseRes> {
                override fun onResponse(
                    call: Call<MainBaseRes>,
                    response: Response<MainBaseRes>
                ) {
                    ProgressUtils.hideProgress()
                    when {
                        response.code() == ApiConstants.SUCCESS -> {
                            baseRes.response_code = response.body()!!.response_code
                            if (!TextUtils.isEmpty(response.body()!!.obj)) {
                                baseRes.message = response.body()!!.obj
                            } else {
                                baseRes.message = response.body()!!.message
                            }
                        }
                        response.errorBody() != null && TextUtils.isEmpty(
                            response.errorBody()!!.string()
                        ) -> {
                            baseRes.response_code = "400"
                            baseRes.message = response.errorBody()!!.string()
                        }
                        else -> {
                            baseRes.response_code = "400"
                            baseRes.message = context.getString(R.string.try_again)
                        }
                    }
                    liveData.value = baseRes
                }

                override fun onFailure(call: Call<MainBaseRes>, t: Throwable) {
                    ProgressUtils.hideProgress()
                    baseRes.response_code = "400"
                    baseRes.message = t.message!!
                    liveData.value = baseRes
                }
            })

        }
        return liveData
    }

    fun getOrderList(): MutableLiveData<OrderListRes> {

        val liveData = MutableLiveData<OrderListRes>()
        val stockResponse = OrderListRes()

        if (!Functions.isConnected(context)) {
            stockResponse.response_code = "400"
            stockResponse.message = context.getString(R.string.no_internet_title)

        } else {

            ProgressUtils.showProgress(context)
            apiInterface?.orderList(PrefUtils.getProfile(context).UserID)
                ?.enqueue(object : Callback<OrderListRes> {
                    override fun onResponse(
                        call: Call<OrderListRes>,
                        response: Response<OrderListRes>
                    ) {
                        ProgressUtils.hideProgress()
                        when {
                            response.code() == ApiConstants.SUCCESS -> {
                                stockResponse.response_code = response.body()!!.response_code
                                stockResponse.obj = response.body()!!.obj
                                stockResponse.message = response.body()!!.message
                            }
                            response.errorBody() != null && TextUtils.isEmpty(
                                response.errorBody()!!.string()
                            ) -> {
                                stockResponse.response_code = "400"
                                stockResponse.message = response.errorBody()!!.string()
                            }
                            else -> {
                                stockResponse.response_code = "400"
                                stockResponse.message = context.getString(R.string.try_again)
                            }
                        }
                        liveData.value = stockResponse
                    }

                    override fun onFailure(call: Call<OrderListRes>, t: Throwable) {
                        ProgressUtils.hideProgress()
                        stockResponse.response_code = "400"
                        stockResponse.message = t.message!!
                        liveData.value = stockResponse
                    }
                })

        }
        return liveData
    }

    fun getNotificationList(): MutableLiveData<NotificationResponse> {

        val liveData = MutableLiveData<NotificationResponse>()
        val notificationResponse = NotificationResponse()

        if (!Functions.isConnected(context)) {
            notificationResponse.response_code = "400"
            notificationResponse.message = context.getString(R.string.no_internet_title)

        } else {

            ProgressUtils.showProgress(context)
            apiInterface?.notificationList(PrefUtils.getProfile(context).UserID)
                ?.enqueue(object : Callback<NotificationResponse> {
                    override fun onResponse(
                        call: Call<NotificationResponse>,
                        response: Response<NotificationResponse>
                    ) {
                        ProgressUtils.hideProgress()
                        when {
                            response.code() == ApiConstants.SUCCESS -> {
                                notificationResponse.response_code = response.body()!!.response_code
                                notificationResponse.obj = response.body()!!.obj
                                notificationResponse.message = response.body()!!.message
                            }
                            response.errorBody() != null && TextUtils.isEmpty(
                                response.errorBody()!!.string()
                            ) -> {
                                notificationResponse.response_code = "400"
                                notificationResponse.message = response.errorBody()!!.string()
                            }
                            else -> {
                                notificationResponse.response_code = "400"
                                notificationResponse.message = context.getString(R.string.try_again)
                            }
                        }
                        liveData.value = notificationResponse
                    }

                    override fun onFailure(call: Call<NotificationResponse>, t: Throwable) {
                        ProgressUtils.hideProgress()
                        notificationResponse.response_code = "400"
                        notificationResponse.message = t.message!!
                        liveData.value = notificationResponse
                    }
                })

        }
        return liveData
    }

    fun getStartUp(): MutableLiveData<StartUpResponse> {

        val liveData = MutableLiveData<StartUpResponse>()
        val stockResponse = StartUpResponse()

        if (!Functions.isConnected(context)) {
            stockResponse.response_code = "400"
            stockResponse.message = context.getString(R.string.no_internet_title)

        } else {

            ProgressUtils.showProgress(context)
            apiInterface?.getStartUp()
                ?.enqueue(object : Callback<StartUpResponse> {
                    override fun onResponse(
                        call: Call<StartUpResponse>,
                        response: Response<StartUpResponse>
                    ) {
                        ProgressUtils.hideProgress()
                        when {
                            response.code() == ApiConstants.SUCCESS -> {
                                stockResponse.response_code = response.body()!!.response_code
                                stockResponse.obj = response.body()!!.obj
                                stockResponse.message = response.body()!!.message
                            }
                            response.errorBody() != null && TextUtils.isEmpty(
                                response.errorBody()!!.string()
                            ) -> {
                                stockResponse.response_code = "400"
                                stockResponse.message = response.errorBody()!!.string()
                            }
                            else -> {
                                stockResponse.response_code = "400"
                                stockResponse.message = context.getString(R.string.try_again)
                            }
                        }
                        liveData.value = stockResponse
                    }

                    override fun onFailure(call: Call<StartUpResponse>, t: Throwable) {
                        ProgressUtils.hideProgress()
                        stockResponse.response_code = "400"
                        stockResponse.message = t.message!!
                        liveData.value = stockResponse
                    }
                })

        }
        return liveData
    }

    fun getWallet(): MutableLiveData<WalletHistoryResponse> {

        val profile = PrefUtils.getProfile(context)

        val liveData = MutableLiveData<WalletHistoryResponse>()
        val stockResponse = WalletHistoryResponse()

        if (!Functions.isConnected(context)) {
            stockResponse.response_code = "400"
            stockResponse.message = context.getString(R.string.no_internet_title)

        } else {

         //   ProgressUtils.showProgress(context)
            apiInterface?.getWallet(profile.UserID)
                ?.enqueue(object : Callback<WalletHistoryResponse> {
                    override fun onResponse(
                        call: Call<WalletHistoryResponse>,
                        response: Response<WalletHistoryResponse>
                    ) {
                      //  ProgressUtils.hideProgress()
                        when {
                            response.code() == ApiConstants.SUCCESS -> {
                                stockResponse.response_code = response.body()!!.response_code
                                stockResponse.obj = response.body()!!.obj
                                stockResponse.message = response.body()!!.message
                            }
                            response.errorBody() != null && TextUtils.isEmpty(
                                response.errorBody()!!.string()
                            ) -> {
                                stockResponse.response_code = "400"
                                stockResponse.message = response.errorBody()!!.string()
                            }
                            else -> {
                                stockResponse.response_code = "400"
                                stockResponse.message = context.getString(R.string.try_again)
                            }
                        }
                        liveData.value = stockResponse
                    }

                    override fun onFailure(call: Call<WalletHistoryResponse>, t: Throwable) {
                       // ProgressUtils.hideProgress()
                        stockResponse.response_code = "400"
                        stockResponse.message = t.message!!
                        liveData.value = stockResponse
                    }
                })

        }
        return liveData
    }
    fun addPaymentdetail(req: AddPaymentRequest): MutableLiveData<PaymentResponse> {

        val liveData = MutableLiveData<PaymentResponse>()
        val paymentRes = PaymentResponse()

        if (!Functions.isConnected(context)) {
            paymentRes.response_code = "400"
            paymentRes.message = context.getString(R.string.no_internet_title)

        } else {

            ProgressUtils.showProgress(context)
            apiInterface?.addpayment(req)?.enqueue(object : Callback<PaymentResponse> {
                override fun onResponse(
                    call: Call<PaymentResponse>,
                    response: Response<PaymentResponse>
                ) {
                    ProgressUtils.hideProgress()
                    when {
                        response.code() == ApiConstants.SUCCESS -> {
                            paymentRes.response_code = response.body()!!.response_code
                            if (paymentRes.response_code == ApiConstants.SUCCESS.toString()) {
                                paymentRes.obj = response.body()!!.obj
                            }
                            paymentRes.message = response.body()!!.message
                        }
                        response.errorBody() != null && TextUtils.isEmpty(
                            response.errorBody()!!.string()
                        ) -> {
                            paymentRes.response_code = "400"
                            paymentRes.message = response.errorBody()!!.string()
                        }
                        else -> {
                            paymentRes.response_code = "400"
                            paymentRes.message = context.getString(R.string.try_again)
                        }
                    }
                    liveData.value = paymentRes
                }

                override fun onFailure(call: Call<PaymentResponse>, t: Throwable) {
                    ProgressUtils.hideProgress()
                    paymentRes.response_code = "400"
                    paymentRes.message = t.message!!
                    liveData.value = paymentRes
                }
            })

        }
        return liveData

    }



    fun addMoney(req: AddMoneyRequest): MutableLiveData<MainBaseRes> {

        val liveData = MutableLiveData<MainBaseRes>()
        val baseRes = MainBaseRes()

        if (!Functions.isConnected(context)) {
            baseRes.response_code = "400"
            baseRes.message = context.getString(R.string.no_internet_title)

        } else {

            ProgressUtils.showProgress(context)
            apiInterface?.addMoney(req)?.enqueue(object : Callback<MainBaseRes> {
                override fun onResponse(
                    call: Call<MainBaseRes>,
                    response: Response<MainBaseRes>
                ) {
                    ProgressUtils.hideProgress()
                    when {
                        response.code() == ApiConstants.SUCCESS -> {
                            baseRes.response_code = response.body()!!.response_code
                            if (!TextUtils.isEmpty(response.body()!!.obj)) {
                                baseRes.message = response.body()!!.obj
                            } else {
                                baseRes.message = response.body()!!.message
                            }
                        }
                        response.errorBody() != null && TextUtils.isEmpty(
                            response.errorBody()!!.string()
                        ) -> {
                            baseRes.response_code = "400"
                            baseRes.message = response.errorBody()!!.string()
                        }
                        else -> {
                            baseRes.response_code = "400"
                            baseRes.message = context.getString(R.string.try_again)
                        }
                    }
                    liveData.value = baseRes
                }

                override fun onFailure(call: Call<MainBaseRes>, t: Throwable) {
                    ProgressUtils.hideProgress()
                    baseRes.response_code = "400"
                    baseRes.message = t.message!!
                    liveData.value = baseRes
                }
            })

        }
        return liveData
    }

    fun getTimeRange(): MutableLiveData<TimeRangeResponse> {

        val liveData = MutableLiveData<TimeRangeResponse>()
        val stockResponse = TimeRangeResponse()

        if (!Functions.isConnected(context)) {
            stockResponse.response_code = "400"
            stockResponse.message = context.getString(R.string.no_internet_title)

        } else {

            ProgressUtils.showProgress(context)
            apiInterface?.getTimeRange()
                ?.enqueue(object : Callback<TimeRangeResponse> {
                    override fun onResponse(
                        call: Call<TimeRangeResponse>,
                        response: Response<TimeRangeResponse>
                    ) {
                        ProgressUtils.hideProgress()
                        when {
                            response.code() == ApiConstants.SUCCESS -> {
                                stockResponse.response_code = response.body()!!.response_code
                                stockResponse.obj = response.body()!!.obj
                                stockResponse.message = response.body()!!.message
                            }
                            response.errorBody() != null && TextUtils.isEmpty(
                                response.errorBody()!!.string()
                            ) -> {
                                stockResponse.response_code = "400"
                                stockResponse.message = response.errorBody()!!.string()
                            }
                            else -> {
                                stockResponse.response_code = "400"
                                stockResponse.message = context.getString(R.string.try_again)
                            }
                        }
                        liveData.value = stockResponse
                    }

                    override fun onFailure(call: Call<TimeRangeResponse>, t: Throwable) {
                        ProgressUtils.hideProgress()
                        stockResponse.response_code = "400"
                        stockResponse.message = t.message!!
                        liveData.value = stockResponse
                    }
                })

        }
        return liveData
    }

    fun getProfile(): MutableLiveData<LoginRes> {

        val profile = PrefUtils.getProfile(context)

        val liveData = MutableLiveData<LoginRes>()
        val loginRes = LoginRes()

        if (!Functions.isConnected(context)) {
            loginRes.response_code = "400"
            loginRes.message = context.getString(R.string.no_internet_title)

        } else {

            ProgressUtils.showProgress(context)
            apiInterface?.getProfile(profile.UserID)?.enqueue(object : Callback<LoginRes> {
                override fun onResponse(
                    call: Call<LoginRes>,
                    response: Response<LoginRes>
                ) {
                    ProgressUtils.hideProgress()
                    when {
                        response.code() == ApiConstants.SUCCESS -> {
                            loginRes.response_code = response.body()!!.response_code
                            if (loginRes.response_code == ApiConstants.SUCCESS.toString()) {
                                loginRes.obj = response.body()!!.obj
                            }
                            loginRes.message = response.body()!!.message
                        }
                        response.errorBody() != null && TextUtils.isEmpty(
                            response.errorBody()!!.string()
                        ) -> {
                            loginRes.response_code = "400"
                            loginRes.message = response.errorBody()!!.string()
                        }
                        else -> {
                            loginRes.response_code = "400"
                            loginRes.message = context.getString(R.string.try_again)
                        }
                    }
                    liveData.value = loginRes
                }

                override fun onFailure(call: Call<LoginRes>, t: Throwable) {
                    ProgressUtils.hideProgress()
                    loginRes.response_code = "400"
                    loginRes.message = t.message!!
                    liveData.value = loginRes
                }
            })

        }
        return liveData

    }

    fun CancelOrder(orderId: Int): MutableLiveData<MainBaseRes> {

        val liveData = MutableLiveData<MainBaseRes>()
        val baseRes = MainBaseRes()

        if (!Functions.isConnected(context)) {
            baseRes.response_code = "400"
            baseRes.message = context.getString(R.string.no_internet_title)

        } else {

            ProgressUtils.showProgress(context)
            apiInterface?.cancelOrder(orderId,PrefUtils.getProfile(context).UserID)?.enqueue(object : Callback<MainBaseRes> {
                override fun onResponse(
                    call: Call<MainBaseRes>,
                    response: Response<MainBaseRes>
                ) {
                    ProgressUtils.hideProgress()
                    when {
                        response.code() == ApiConstants.SUCCESS -> {
                            baseRes.response_code = response.body()!!.response_code
                            if (!TextUtils.isEmpty(response.body()!!.obj)) {
                                baseRes.message = response.body()!!.obj
                            } else {
                                baseRes.message = response.body()!!.message
                            }
                        }
                        response.errorBody() != null && TextUtils.isEmpty(
                            response.errorBody()!!.string()
                        ) -> {
                            baseRes.response_code = "400"
                            baseRes.message = response.errorBody()!!.string()
                        }
                        else -> {
                            baseRes.response_code = "400"
                            baseRes.message = context.getString(R.string.try_again)
                        }
                    }
                    liveData.value = baseRes
                }

                override fun onFailure(call: Call<MainBaseRes>, t: Throwable) {
                    ProgressUtils.hideProgress()
                    baseRes.response_code = "400"
                    baseRes.message = t.message!!
                    liveData.value = baseRes
                }
            })

        }
        return liveData
    }



}