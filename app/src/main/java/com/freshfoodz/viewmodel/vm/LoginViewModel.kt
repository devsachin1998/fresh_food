package com.freshfoodz.viewmodel.vm

import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.freshfoodz.R
import com.freshfoodz.api.ApiClient
import com.freshfoodz.api.ApiConstants
import com.freshfoodz.databinding.ActivityLoginBinding
import com.freshfoodz.helper.PrefUtils
import com.freshfoodz.helper.fireIntent
import com.freshfoodz.helper.isNullEmpty
import com.freshfoodz.helper.value
import com.freshfoodz.model.LoginReq
import com.freshfoodz.ui.DashboardActivity
import com.freshfoodz.ui.PaymentActivity
import com.freshfoodz.viewmodel.BaseInterface
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId

class LoginViewModel(
    private var context: Context,
    private var callBack: LoginCallBack,
    private var fcmtoken: String
) :
    ViewModel() {

    fun doLogin(binding: ActivityLoginBinding) {
        if (binding.edtUsername.isNullEmpty()) {
            callBack.onError("Username is required")
            return
        }
        if (binding.edtPassword.isNullEmpty()) {
            callBack.onError("Password is required")
            return
        }
        if (binding.edtPassword.value().length < context.resources.getInteger(R.integer.pwd_min)) {
            callBack.onError("Password must be atleast of " + context.resources.getInteger(R.integer.pwd_min) + " characters")
            return
        }
        try {
            FirebaseInstanceId.getInstance().instanceId
                .addOnCompleteListener(OnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        return@OnCompleteListener
                    }
                    // Get new Instance ID token
                    val fcmtoken1 = task.result?.token
                    fcmtoken=fcmtoken1.toString();
                })
        } catch (ex: Exception) {

        }

        ApiClient(context).doLogin(
            LoginReq(
                binding.edtPassword.value(),
                binding.edtUsername.value(),
                fcmtoken,
                GetAppVersion(context)
            )
        ).observe(context as AppCompatActivity, {
            if (it.response_code == ApiConstants.SUCCESS.toString()) {
                PrefUtils.saveBoolean(context, PrefUtils.IS_LOGGED_IN, true)
                PrefUtils.saveObjectValue(context, PrefUtils.USER_PROFILE, it.obj)
               // context.fireIntent(PaymentActivity::class.java, true)
                context.fireIntent(DashboardActivity::class.java, true)

            } else {
                callBack.onError(it.message)
            }
        })
    }

    interface LoginCallBack : BaseInterface {

    }
    fun GetAppVersion(context: Context): String {
        var version = ""
        try {
            val pInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            version = pInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return version
    }
}