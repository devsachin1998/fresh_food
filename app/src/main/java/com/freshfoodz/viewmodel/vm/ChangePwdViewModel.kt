package com.freshfoodz.viewmodel.vm

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.freshfoodz.R
import com.freshfoodz.api.ApiClient
import com.freshfoodz.api.ApiConstants
import com.freshfoodz.databinding.ActivityChangePasswordBinding
import com.freshfoodz.helper.*
import com.freshfoodz.model.ChangePwdReq
import com.freshfoodz.ui.LoginActivity
import com.freshfoodz.viewmodel.BaseInterface

class ChangePwdViewModel(private var context: Context, private var callBack: BaseInterface) :
    ViewModel() {

    fun changePassword(binding: ActivityChangePasswordBinding) {
        if (binding.edtNewPassword.isNullEmpty()) {
            callBack.onError("New password is required")
            return
        }
        if (binding.edtNewPassword.value().length < context.resources.getInteger(R.integer.pwd_min)) {
            callBack.onError("New password must be atleast of " + context.resources.getInteger(R.integer.pwd_min) + " characters")
            return
        }
        if (binding.edtConfirmPassword.isNullEmpty()) {
            callBack.onError("Confirm password is required")
            return
        }
        if (binding.edtConfirmPassword.value().length < context.resources.getInteger(R.integer.pwd_min)) {
            callBack.onError("Confirm password must be atleast of " + context.resources.getInteger(R.integer.pwd_min) + " characters")
            return
        }
        if (binding.edtNewPassword.value() != binding.edtConfirmPassword.value()) {
            callBack.onError("New password and confirm password must be same")
            return
        }

        val userId = PrefUtils.getProfile(context).UserID
        ApiClient(context).doChangePwd(ChangePwdReq(binding.edtConfirmPassword.value(), userId))
            .observe(context as AppCompatActivity, {
                if (it.response_code == ApiConstants.SUCCESS.toString()) {

                    /*val profile = PrefUtils.getProfile(context)
                    profile.Password = binding.edtNewPassword.value()
                    PrefUtils.saveObjectValue(context, PrefUtils.USER_PROFILE, profile)*/

                    context.toast(it.message)

                    countDownTimer(1000) {
                        context.fireIntent(LoginActivity::class.java, true)
                    }

                } else {
                    callBack.onError(it.message)
                }
            })
    }

}