package com.freshfoodz.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.freshfoodz.R
import com.freshfoodz.databinding.ActivityLoginBinding
import com.freshfoodz.helper.*
import com.freshfoodz.viewmodel.factory.LoginViewModelFactory
import com.freshfoodz.viewmodel.vm.LoginViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.gun0912.tedpermission.provider.TedPermissionProvider.context

class LoginActivity : AppCompatActivity(), LoginViewModel.LoginCallBack {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel
private var fcmtoken =""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        init()
    }

    private fun init() {



        initClick()

        val spannableString = AdvancedSpannableString("New User? Create an account")
        spannableString.setUnderLine("Create an account")
        binding.txtRegistrationLabel.text = spannableString

        viewModel = ViewModelProvider(
            this,
            LoginViewModelFactory(this, this, PrefUtils.getStringValue(context, "fcmtoken"))
        ).get(LoginViewModel::class.java)
    }

    private fun initClick() {
        binding.txtRegistrationLabel.setOnClickListener {
            fireIntent(RegistrationActivity::class.java)
        }
        binding.btnLogin.setOnClickListener { viewModel.doLogin(binding) }
        binding.edtPassword.onDone { viewModel.doLogin(binding) }
    }

    override fun onError(error: String) {
        toast(error)
    }

}