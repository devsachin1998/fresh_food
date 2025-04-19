package com.freshfoodz.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.freshfoodz.R
import com.freshfoodz.databinding.ActivityChangePasswordBinding
import com.freshfoodz.helper.closeScreen
import com.freshfoodz.helper.onDone
import com.freshfoodz.helper.toast
import com.freshfoodz.viewmodel.BaseInterface
import com.freshfoodz.viewmodel.factory.ChangePwdViewModelFactory
import com.freshfoodz.viewmodel.vm.ChangePwdViewModel

class ChangePasswordActivity : AppCompatActivity(), BaseInterface {

    private lateinit var binding: ActivityChangePasswordBinding
    private lateinit var viewModel: ChangePwdViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_change_password)
        init()
    }

    private fun init() {
        setSupportActionBar(binding.toolbarLayout.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbarLayout.toolbar.title = "Change Password"

        viewModel = ViewModelProvider(
            this,
            ChangePwdViewModelFactory(this, this)
        ).get(ChangePwdViewModel::class.java)

        initClick()

    }

    private fun initClick() {
        binding.btnChange.setOnClickListener { viewModel.changePassword(binding) }
        binding.toolbarLayout.toolbar.setNavigationOnClickListener { closeScreen() }
        binding.edtConfirmPassword.onDone { viewModel.changePassword(binding) }
    }

    override fun onBackPressed() {
        closeScreen()
    }

    override fun onError(error: String) {
        toast(error)
    }
}