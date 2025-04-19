package com.freshfoodz.viewmodel.vm

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.freshfoodz.R
import com.freshfoodz.api.ApiClient
import com.freshfoodz.api.ApiConstants
import com.freshfoodz.helper.OnOptionListener
import com.freshfoodz.helper.PrefUtils
import com.freshfoodz.helper.fireIntent
import com.freshfoodz.helper.showAlert
import com.freshfoodz.ui.DashboardActivity
import com.freshfoodz.ui.LoginActivity
import com.freshfoodz.ui.MainActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.gun0912.tedpermission.provider.TedPermissionProvider

class SplashViewModel(private var context: Context) :
    ViewModel() {

    fun startUp() {
       var fcm= PrefUtils.getStringValue(TedPermissionProvider.context, "fcmtoken")
        if(fcm.equals(""))
        {
            try {
                FirebaseInstanceId.getInstance().instanceId
                    .addOnCompleteListener(OnCompleteListener { task ->
                        if (!task.isSuccessful) {
                            return@OnCompleteListener
                        }
                        // Get new Instance ID token
                        val fcmtoken1 = task.result?.token
                        PrefUtils.saveStringValue(context, "fcmtoken", fcmtoken1.toString());
                        ApiClient(context).getStartUp().observe(context as AppCompatActivity, {

                            if (it.response_code == ApiConstants.SUCCESS.toString()) {
                                PrefUtils.saveObjectValue(context, PrefUtils.START_UP, it.obj)
                            }
                            if (PrefUtils.getBoolean(context, PrefUtils.IS_LOGGED_IN)) {
                                context.fireIntent(DashboardActivity::class.java, true)
                            } else {
                                context.fireIntent(LoginActivity::class.java, true)
                            }

                        })

                    })
            } catch (ex: Exception) {

            }
        }
        else
        {
            ApiClient(context).getStartUp().observe(context as AppCompatActivity, {

                if (it.response_code == ApiConstants.SUCCESS.toString()) {
                    PrefUtils.saveObjectValue(context, PrefUtils.START_UP, it.obj)
                }
                if (PrefUtils.getBoolean(context, PrefUtils.IS_LOGGED_IN)) {
                    context.fireIntent(DashboardActivity::class.java, true)
                } else {
                    context.fireIntent(LoginActivity::class.java, true)
                }

            })
        }


    }



}