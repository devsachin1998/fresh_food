package com.freshfoodz.viewmodel.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.freshfoodz.viewmodel.vm.RegisterViewModel

class RegisterViewModelFactory(
    private var context: Context,
    private var callBack: RegisterViewModel.RegisterCallBack
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RegisterViewModel(context, callBack) as T
    }
}