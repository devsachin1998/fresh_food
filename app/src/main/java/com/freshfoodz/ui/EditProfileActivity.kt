package com.freshfoodz.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.freshfoodz.R
import com.freshfoodz.databinding.ActivityEditProfileBinding
import com.freshfoodz.helper.PrefUtils
import com.freshfoodz.helper.closeScreen
import com.freshfoodz.helper.toast
import com.freshfoodz.model.LoginRes
import com.freshfoodz.viewmodel.factory.RegisterViewModelFactory
import com.freshfoodz.viewmodel.vm.RegisterViewModel

class EditProfileActivity : AppCompatActivity(), RegisterViewModel.RegisterCallBack {

    private lateinit var viewModel: RegisterViewModel
    private lateinit var binding: ActivityEditProfileBinding
    private lateinit var profile: LoginRes.UserProfile

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_profile)
        init()
    }

    private fun init() {
        setSupportActionBar(binding.toolbarLayout.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbarLayout.toolbar.title = "Edit Profile"

        viewModel = ViewModelProvider(
            this,
            RegisterViewModelFactory(this, this)
        ).get(RegisterViewModel::class.java)

        profile = PrefUtils.getProfile(this)
        binding.edtName.setText(profile.Name)
        binding.edtUsername.setText(profile.UserName)
        binding.edtMobile.setText(profile.MobileNo)
        binding.edtEmail.setText(profile.Email)
        binding.edtAddress.setText(profile.Address)

        initClick()
    }

    private fun initClick() {
        binding.toolbarLayout.toolbar.setNavigationOnClickListener { closeScreen() }
        binding.btnRegister.setOnClickListener {
            viewModel.doUpdate(
                binding,
                profile.Password,
                profile.UserID
            )
        }
    }

    override fun onError(error: String) {
        toast(error)
    }

    override fun onBackPressed() {
        closeScreen()
    }
}