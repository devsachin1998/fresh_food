package com.freshfoodz.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.freshfoodz.R
import com.freshfoodz.databinding.ActivityRegistrationBinding
import com.freshfoodz.helper.AdvancedSpannableString
import com.freshfoodz.helper.fireIntent
import com.freshfoodz.helper.onDone
import com.freshfoodz.helper.toast
import com.freshfoodz.viewmodel.factory.RegisterViewModelFactory
import com.freshfoodz.viewmodel.vm.RegisterViewModel

class RegistrationActivity : AppCompatActivity(), RegisterViewModel.RegisterCallBack {

    private lateinit var binding: ActivityRegistrationBinding
    private lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_registration)
        init()
    }

    private fun init() {

        viewModel = ViewModelProvider(
            this,
            RegisterViewModelFactory(this, this)
        ).get(RegisterViewModel::class.java)
        initClick()

        val spannableString = AdvancedSpannableString("Already have an account? Login")
        spannableString.setUnderLine("Login")
        binding.txtLoginLabel.text = spannableString
    }

    private fun initClick() {
        binding.txtLoginLabel.setOnClickListener {
            fireIntent(
                LoginActivity::class.java,
                true
            )
        }
        binding.btnRegister.setOnClickListener {
            viewModel.doRegister(binding)
        }
        binding.edtPassword.onDone { viewModel.doRegister(binding) }
    }

    override fun onError(error: String) {
        toast(error)
    }
}