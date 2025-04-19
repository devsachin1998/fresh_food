package com.freshfoodz.viewmodel.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.freshfoodz.viewmodel.vm.LoginViewModel

class LoginViewModelFactory(
    private var context: Context,
    private var callBack: LoginViewModel.LoginCallBack,
    private var fcmtoken:String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return LoginViewModel(context, callBack,fcmtoken) as T
    }
}