package com.freshfoodz.viewmodel.vm

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.freshfoodz.api.ApiClient
import com.freshfoodz.api.ApiConstants
import com.freshfoodz.helper.PrefUtils
import com.freshfoodz.viewmodel.BaseInterface

class ProfileViewModel(private var context: Context, private var callBack: ProfileCallBack) :
    ViewModel() {

    fun getProfile() {
        ApiClient(context).getProfile().observe(context as AppCompatActivity, {
            if (it.response_code == ApiConstants.SUCCESS.toString()) {
                PrefUtils.saveBoolean(context, PrefUtils.IS_LOGGED_IN, true)
                PrefUtils.saveObjectValue(context, PrefUtils.USER_PROFILE, it.obj)
                callBack.refresh()
            } else {
                callBack.onError(it.message)
            }
        })
    }

    interface ProfileCallBack : BaseInterface {
        fun refresh()
    }
}