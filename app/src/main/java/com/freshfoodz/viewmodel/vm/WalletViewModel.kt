package com.freshfoodz.viewmodel.vm

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.freshfoodz.api.ApiClient
import com.freshfoodz.api.ApiConstants
import com.freshfoodz.model.WalletHistoryResponse
import com.freshfoodz.viewmodel.BaseInterface

class WalletViewModel(private var context: Context, private var callBack: WalletCallBack) :
    ViewModel() {

    fun getWalletHistory() {

        ApiClient(context).getWallet().observe(context as AppCompatActivity, {
            if (it.response_code == ApiConstants.SUCCESS.toString()) {
                if (it.obj._historyList.isEmpty()) {
                    callBack.onEmpty()
                } else {
                    callBack.onWallet(it.obj)
                }
            } else {
                callBack.onError(it.message)
            }
        })
    }

    interface WalletCallBack : BaseInterface {
        fun onEmpty()
        fun onWallet(obj: WalletHistoryResponse.Wallet)
    }
}