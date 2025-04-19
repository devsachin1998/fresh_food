package com.freshfoodz.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.freshfoodz.R
import com.freshfoodz.databinding.ActivitySignupDoneBinding
import com.freshfoodz.helper.fireIntent

class SignupDoneActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupDoneBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup_done)
        binding.btnLogin.setOnClickListener { fireIntent(LoginActivity::class.java) }
    }
}