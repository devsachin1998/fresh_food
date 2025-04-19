package com.freshfoodz.ui

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.freshfoodz.R
import com.freshfoodz.adapter.OrderAdapter
import com.freshfoodz.api.ApiClient
import com.freshfoodz.api.ApiConstants
import com.freshfoodz.databinding.ActivityMyOrdersBinding
import com.freshfoodz.helper.*
import com.freshfoodz.model.StartUpResponse

class MyOrdersActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyOrdersBinding
    private lateinit var adapter: OrderAdapter
    private var isFromOrder = false
    private lateinit var startUp: StartUpResponse.StartUp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_orders)
        init()
        isFromOrder = intent.extras?.getBoolean(AppConstants.FROM_ORDER) ?: false
    }

    private fun init() {
        setSupportActionBar(binding.toolbarLayout.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbarLayout.toolbar.title = "My Orders"

        startUp = PrefUtils.getStartUp(this)
       // binding.toolbarLayout.imgProfile.setImageResource(R.drawable.ic_call)
        if (TextUtils.isEmpty(startUp.HelpLineNo)) {
            binding.toolbarLayout.imgProfile.visibility = View.VISIBLE
        } else {
            binding.toolbarLayout.imgProfile.visibility = View.GONE
            binding.toolbarLayout.imgcall.visibility = View.GONE
        }

        adapter = OrderAdapter()
        binding.rvOrders.adapter = adapter


//        getOrders()
        initClick()

    }
    override fun onResume() {
        super.onResume()
        getOrders()
        // Update UI or reload data here
    }
    private fun getOrders() {
        ApiClient(this).getOrderList().observe(this, {
            if (it.response_code == ApiConstants.SUCCESS.toString()) {
                if (it.obj.isNotEmpty()) {
                    binding.emptyLayout.txtEmpty.visibility = View.GONE
                    adapter.setItems(it.obj)
                } else {
                    binding.emptyLayout.txtEmpty.visibility = View.VISIBLE
                    binding.emptyLayout.txtEmpty.text = "No orders"
                }
            } else {
                binding.emptyLayout.txtEmpty.visibility = View.GONE
                toast(it.message)
            }
        })
    }

    private fun initClick() {
        binding.toolbarLayout.toolbar.setNavigationOnClickListener {
            if (isFromOrder) {
                fireIntent(DashboardActivity::class.java, true)
            } else {
                closeScreen()
            }
        }

        binding.toolbarLayout.imgProfile.setOnClickListener {
            callIntent(startUp.HelpLineNo)
        }
    }

    override fun onBackPressed() {
        if (isFromOrder) {
            fireIntent(DashboardActivity::class.java, true)
        } else {
            closeScreen()
        }
    }
}