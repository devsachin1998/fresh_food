package com.freshfoodz.viewmodel.vm

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.freshfoodz.R
import com.freshfoodz.api.ApiClient
import com.freshfoodz.api.ApiConstants
import com.freshfoodz.databinding.ActivityEditProfileBinding
import com.freshfoodz.databinding.ActivityRegistrationBinding
import com.freshfoodz.helper.*
import com.freshfoodz.model.RegisterUpdateReq
import com.freshfoodz.ui.SignupDoneActivity
import com.freshfoodz.viewmodel.BaseInterface

class RegisterViewModel(private var context: Context, private var callBack: RegisterCallBack) :
    ViewModel() {

    fun doRegister(binding: ActivityRegistrationBinding) {
        if (binding.edtName.isNullEmpty()) {
            callBack.onError("Name is required")
            return
        }
//        if (binding.edtUsername.isNullEmpty()) {
//            callBack.onError("Username is required")
//            return
//        }
        if (binding.edtMobile.isNullEmpty()) {
            callBack.onError("Mobile number is required")
            return
        }
        if (binding.edtMobile.value().length != 10) {
            callBack.onError("Mobile number is not valid")
            return
        }
        if (binding.edtEmail.isNullEmpty()) {
            callBack.onError("Email-id is required")
            return
        }
        if (emailNotValid(binding.edtEmail.value())) {
            callBack.onError("Email-id is not valid")
            return
        }
        if (binding.edtAddress.isNullEmpty()) {
            callBack.onError("Address is required")
            return
        }
        if (binding.edtPassword.isNullEmpty()) {
            callBack.onError("Password is required")
            return
        }
        if (binding.edtPassword.value().length < context.resources.getInteger(R.integer.pwd_min)) {
            callBack.onError("Password must be at least of " + context.resources.getInteger(R.integer.pwd_min) + " characters")
            return
        }

        ApiClient(context).doRegisterUpdate(
            RegisterUpdateReq(
                binding.edtEmail.value(),
                binding.edtMobile.value(),
                binding.edtName.value(),
                binding.edtPassword.value(), 4, 0,
                binding.edtName.value(), binding.edtAddress.value()
            )
        ).observe(context as AppCompatActivity, {
            if (it.response_code == ApiConstants.SUCCESS.toString()) {
                context.fireIntent(SignupDoneActivity::class.java, true)
            } else {
                callBack.onError(it.message)
            }
        })
    }

    fun doUpdate(binding: ActivityEditProfileBinding, password: String, userID: Int) {
        if (binding.edtName.isNullEmpty()) {
            callBack.onError("Name is required")
            return
        }
        if (binding.edtUsername.isNullEmpty()) {
            callBack.onError("Username is required")
            return
        }
        if (binding.edtMobile.isNullEmpty()) {
            callBack.onError("Mobile number is required")
            return
        }
        if (binding.edtMobile.value().length != 10) {
            callBack.onError("Mobile number is not valid")
            return
        }
        if (binding.edtEmail.isNullEmpty()) {
            callBack.onError("Email-id is required")
            return
        }
        if (emailNotValid(binding.edtEmail.value())) {
            callBack.onError("Email-id is not valid")
            return
        }
        if (binding.edtAddress.isNullEmpty()) {
            callBack.onError("Address is required")
            return
        }

        ApiClient(context).doRegisterUpdate(
            RegisterUpdateReq(
                binding.edtEmail.value(),
                binding.edtMobile.value(),
                binding.edtName.value(),
                password, 4, userID,
                binding.edtUsername.value(),
                binding.edtAddress.value()
            )
        ).observe(context as AppCompatActivity, {
            if (it.response_code == ApiConstants.SUCCESS.toString()) {

                val profile = PrefUtils.getProfile(context)
                profile.Name = binding.edtName.value()
                profile.UserName = binding.edtUsername.value()
                profile.MobileNo = binding.edtMobile.value()
                profile.Email = binding.edtEmail.value()
                PrefUtils.saveObjectValue(context, PrefUtils.USER_PROFILE, profile)

                context.toast(it.message)
                context.closeScreen()
            } else {
                callBack.onError(it.message)
            }
        })
    }

    interface RegisterCallBack : BaseInterface
}