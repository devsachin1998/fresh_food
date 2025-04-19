package com.freshfoodz.viewmodel.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.freshfoodz.viewmodel.vm.WalletViewModel

class WalletViewModelFactory(
    private var context: Context,
    private var callBack: WalletViewModel.WalletCallBack
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return WalletViewModel(context, callBack) as T
    }
}