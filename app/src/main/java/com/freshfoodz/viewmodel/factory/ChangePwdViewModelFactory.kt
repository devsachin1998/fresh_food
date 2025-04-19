package com.freshfoodz.viewmodel.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.freshfoodz.viewmodel.BaseInterface
import com.freshfoodz.viewmodel.vm.ChangePwdViewModel

class ChangePwdViewModelFactory(
    private var context: Context,
    private var callBack: BaseInterface
) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ChangePwdViewModel(context, callBack) as T
    }
}