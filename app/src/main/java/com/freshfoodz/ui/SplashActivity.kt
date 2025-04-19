package com.freshfoodz.ui

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.freshfoodz.R
import com.freshfoodz.helper.PrefUtils
import com.freshfoodz.viewmodel.factory.SplashViewModelFactory
import com.freshfoodz.viewmodel.vm.SplashViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.gun0912.tedpermission.provider.TedPermissionProvider.context

class SplashActivity : AppCompatActivity() {

    private lateinit var viewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)



        try {
            FirebaseInstanceId.getInstance().instanceId
                .addOnCompleteListener(OnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        return@OnCompleteListener
                    }
                    // Get new Instance ID token
                    val fcmtoken1 = task.result?.token
                    PrefUtils.saveStringValue(this, "fcmtoken", fcmtoken1.toString());

                })
        } catch (ex: Exception) {

        }
        val handler = Handler()
        val runnable = Runnable {
            viewModel =
                ViewModelProvider(this, SplashViewModelFactory(this)).get(SplashViewModel::class.java)
            viewModel.startUp()
        }

        handler.postDelayed(runnable, 2000)

    }
}