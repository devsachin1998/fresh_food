package com.freshfoodz.viewmodel.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.freshfoodz.viewmodel.vm.ProfileViewModel

class ProfileViewModelFactory(
    private var context: Context,
    private var callBack: ProfileViewModel.ProfileCallBack
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProfileViewModel(context, callBack) as T
    }
}