package com.freshfoodz.ui

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.freshfoodz.R
import com.freshfoodz.adapter.NotificationAdapter
import com.freshfoodz.adapter.OrderAdapter
import com.freshfoodz.api.ApiClient
import com.freshfoodz.api.ApiConstants
import com.freshfoodz.databinding.ActivityMyOrdersBinding
import com.freshfoodz.databinding.ActivityNotificationBinding
import com.freshfoodz.helper.*
import com.freshfoodz.model.StartUpResponse

class NotificationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotificationBinding
    private lateinit var adapter: NotificationAdapter
    private var isFromOrder = false
    private lateinit var startUp: StartUpResponse.StartUp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notification)
        init()

    }

    private fun init() {
        setSupportActionBar(binding.toolbarLayout.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbarLayout.toolbar.title = "My Notification"

        startUp = PrefUtils.getStartUp(this)
        // binding.toolbarLayout.imgProfile.setImageResource(R.drawable.ic_call)
        if (TextUtils.isEmpty(startUp.HelpLineNo)) {
            binding.toolbarLayout.imgProfile.visibility = View.VISIBLE
        } else {
            binding.toolbarLayout.imgProfile.visibility = View.GONE
            binding.toolbarLayout.imgcall.visibility = View.GONE
        }

        adapter = NotificationAdapter()
        binding.rvOrders.adapter = adapter


        getNotification()
        initClick()

    }

    private fun getNotification() {
        ApiClient(this).getNotificationList().observe(this, {
            if (it.response_code == ApiConstants.SUCCESS.toString()) {
                if (it.obj.isNotEmpty()) {
                    binding.emptyLayout.txtEmpty.visibility = View.GONE
                    adapter.setItems(it.obj)
                } else {
                    binding.emptyLayout.txtEmpty.visibility = View.VISIBLE
                    binding.emptyLayout.txtEmpty.text = "No Notification"
                }
            } else {
                binding.emptyLayout.txtEmpty.visibility = View.GONE
                toast(it.message)
            }
        })
    }

    private fun initClick() {
        binding.toolbarLayout.toolbar.setNavigationOnClickListener {

                fireIntent(DashboardActivity::class.java, true)
        }

       
    }

    override fun onBackPressed() {

            fireIntent(DashboardActivity::class.java, true)

    }
}