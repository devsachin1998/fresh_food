package com.freshfoodz.ui

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.freshfoodz.R
import com.freshfoodz.adapter.OrderDetailAdpter
import com.freshfoodz.api.ApiClient
import com.freshfoodz.api.ApiConstants
import com.freshfoodz.databinding.ActivityMyOrdersDetailBinding
import com.freshfoodz.helper.*
import com.freshfoodz.model.OrderListRes
import com.freshfoodz.model.StartUpResponse

class MyOrdersDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyOrdersDetailBinding
    private lateinit var adapter: OrderDetailAdpter
    private var isFromOrder = false
    private var orderno ="0";
    private var Date ="0";
    private var Time ="0";
    private var Status ="0";
    private var index =0;
    private var price="0"

    private lateinit var startUp: StartUpResponse.StartUp
    private var items = arrayListOf<OrderListRes.Order>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_orders_detail)
        index = (intent.extras?.getInt("Index") ?: 0)
        orderno = (intent.extras?.getString("OrderNo") ?: "")
        Date = (intent.extras?.getString("Date") ?: "")
        Time = (intent.extras?.getString("Time") ?: "")
        Status = (intent.extras?.getString("Status") ?: "")
        price = (intent.extras?.getString("Price") ?: "0")


        init()
        isFromOrder = intent.extras?.getBoolean(AppConstants.FROM_ORDER) ?: false


    }

    private fun init() {
        setSupportActionBar(binding.toolbarLayout.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbarLayout.toolbar.title = "Orders Details"

        startUp = PrefUtils.getStartUp(this)
        // binding.toolbarLayout.imgProfile.setImageResource(R.drawable.ic_call)
        if (TextUtils.isEmpty(startUp.HelpLineNo)) {
            binding.toolbarLayout.imgProfile.visibility = View.VISIBLE
        } else {
            binding.toolbarLayout.imgProfile.visibility = View.GONE
            binding.toolbarLayout.imgcall.visibility = View.GONE
        }
        adapter = OrderDetailAdpter()
        binding.rvOrders.adapter = adapter
        binding.orderidtext.text=orderno;
        binding.textDate.text=Date;
        binding.textTimeSlot.text=Time;
        binding.textStatus.text=Status;
        binding.pricetxt.text="RS. "+price;
        binding.payamounttxt.text="RS. "+price;
        if(Status=="Accepted")
        {
            binding.cancelBtn.visibility=View.VISIBLE;
        }
        else
        {
            binding.cancelBtn.visibility=View.GONE;
        }

        getOrders()
        initClick()
        binding.cancelBtn.setOnClickListener {
            cancelOrders()
        }
    }

    private fun getOrders() {
        ApiClient(this).getOrderList().observe(this, {
            if (it.response_code == ApiConstants.SUCCESS.toString()) {
                if (it.obj.isNotEmpty()) {
                    binding.emptyLayout.txtEmpty.visibility = View.GONE
                    adapter.setItems(it.obj[index]._itemList)
                    items.add(it.obj[index]);
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
    private fun cancelOrders() {
        ApiClient(this).CancelOrder(orderno.toInt()).observe(this, {
            if (it.response_code == ApiConstants.SUCCESS.toString()) {
                if (it.obj.isNotEmpty()) {

                    toast(it.message)
                    val intent = Intent(this, MyOrdersActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                    startActivity(intent)
                } else {
                    toast(it.message)
                    val intent = Intent(this, MyOrdersActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                    startActivity(intent)

                }
            } else {
                val intent = Intent(this, MyOrdersActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)

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