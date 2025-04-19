package com.freshfoodz.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.freshfoodz.R
import com.freshfoodz.databinding.ActivityProfileBinding
import com.freshfoodz.helper.*
import com.freshfoodz.model.LoginRes
import com.freshfoodz.room.FoodDBHelper
import com.freshfoodz.viewmodel.factory.ProfileViewModelFactory
import com.freshfoodz.viewmodel.vm.ProfileViewModel

class ProfileActivity : AppCompatActivity(), ProfileViewModel.ProfileCallBack {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var profile: LoginRes.UserProfile
    private lateinit var viewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)
        init()
    }

    private fun init() {
        setSupportActionBar(binding.toolbarLayout.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbarLayout.toolbar.title = "Profile"

        viewModel = ViewModelProvider(
            this,
            ProfileViewModelFactory(this, this)
        ).get(ProfileViewModel::class.java)

        initClick()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getProfile()
    }

    private fun setUserDetails() {
        profile = PrefUtils.getProfile(this)
        binding.txtName.text = profile.Name
        binding.txtNumber.text = profile.MobileNo
        binding.txtEmail.text = profile.Email
    }

    private fun initClick() {
        binding.txtChangePassword.setOnClickListener { fireIntent(ChangePasswordActivity::class.java) }
        binding.editLayout.setOnClickListener { fireIntent(EditProfileActivity::class.java) }
        binding.txtMyOrders.setOnClickListener { fireIntent(MyOrdersActivity::class.java) }
        binding.txtWallet.setOnClickListener { fireIntent(WalletActivity::class.java) }
        binding.toolbarLayout.toolbar.setNavigationOnClickListener { closeScreen() }
        binding.txtLogout.setOnClickListener { showAlert() }
    }

    private fun showAlert() {
        showAlert("Logout", "Are you sure you want to logout?", "Yes", "No",true,
            object : OnOptionListener {
                override fun onClick(isYes: Boolean) {
                    if (isYes) {
                        clearSession()
                    }
                }
            })
    }

    private fun clearSession() {
        PrefUtils.saveBoolean(this, PrefUtils.IS_LOGGED_IN, false)
        FoodDBHelper.clearCart(this)
        fireIntent(SplashActivity::class.java)
    }

    override fun onBackPressed() {
        closeScreen()
    }

    override fun refresh() {
        setUserDetails()
    }

    override fun onError(error: String) {
        setUserDetails()
    }
}