package com.freshfoodz.viewmodel.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.freshfoodz.helper.PrefUtils
import com.freshfoodz.viewmodel.vm.SplashViewModel
import com.google.firebase.iid.FirebaseInstanceId

class SplashViewModelFactory(
    private var context: Context
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SplashViewModel(context) as T
    }


}