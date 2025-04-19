package com.freshfoodz.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.freshfoodz.R
import com.freshfoodz.api.ApiClient
import com.freshfoodz.api.ApiConstants
import com.freshfoodz.databinding.ActivityAddressBinding
import com.freshfoodz.helper.*
import com.freshfoodz.model.LoginRes
import com.freshfoodz.model.OrderPlaceReq
import com.freshfoodz.model.SubItem
import com.freshfoodz.model.TimeRangeResponse
import com.freshfoodz.room.FoodDBHelper
import java.text.SimpleDateFormat
import java.util.*


class AddressActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddressBinding
    private lateinit var profile: LoginRes.UserProfile
    private var cartItems = arrayListOf<SubItem>()
    private var totalValue = 0
    private var timeRange = arrayListOf<TimeRangeResponse.Range>()
    private var timeRangeString = arrayListOf<String>()
    private var rangeId = 0
    private var todayDate =
        (Calendar.getInstance()
            .get(Calendar.DAY_OF_MONTH) + 1).toString() + "/" + (Calendar.getInstance()
            .get(Calendar.MONTH) + 1) + "/" + Calendar.getInstance().get(Calendar.YEAR)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_address)
        init()
    }

    private fun init() {
        setSupportActionBar(binding.toolbarLayout.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbarLayout.toolbar.title = "Add details"

        profile = PrefUtils.getProfile(this)

        binding.edDeliveryAddress.setText(profile.Address)
        updateCart()
        initClick()

        ApiClient(this).getTimeRange().observe(this, {
            if (it.response_code == ApiConstants.SUCCESS.toString()) {
                if (it.obj.isNotEmpty()) {
                    binding.timeInput.visibility = View.VISIBLE
                    timeRange.addAll(it.obj)
                    for (range in it.obj) {
                        timeRangeString.add(range.Range)
                    }
                } else {
                    binding.timeInput.visibility = View.GONE
                }
            } else {
                binding.timeInput.visibility = View.GONE
            }
        })
    }

    private fun initClick() {
        binding.toolbarLayout.toolbar.setNavigationOnClickListener { closeScreen() }
        binding.layoutPlace.setOnClickListener {
            createOrder()
        }
        binding.edRemark.onDone { createOrder() }

        val cal = Calendar.getInstance()
        cal.add(Calendar.DATE, 1)
        val c = cal.time
        val df = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())
        val formattedDate: String = df.format(c)

        binding.txtDeliveryDate.text = "Delivery date will be $formattedDate"
        binding.edtSelectTime.setOnClickListener {
            val builderSingle: AlertDialog.Builder = AlertDialog.Builder(this)
            builderSingle.setIcon(R.drawable.ic_time)
            builderSingle.setTitle("Select delivery time")

            val arrayAdapter = ArrayAdapter<String>(
                this,
                android.R.layout.select_dialog_singlechoice
            )
            for (range in timeRange) {
                arrayAdapter.add(range.Range)
            }

            builderSingle.setNegativeButton(
                "cancel"
            ) { dialog, which -> dialog.dismiss() }

            builderSingle.setAdapter(
                arrayAdapter
            ) { dialog, which ->
                val strName = arrayAdapter.getItem(which)
                binding.edtSelectTime.setText(strName)
                for (range in timeRange) {
                    if (range.Range.equals(strName, true)) {
                        rangeId = range.RangeID
                    }
                }
            }
            builderSingle.show()
        }
    }

    private fun createOrder() {
        if (binding.edDeliveryAddress.isNullEmpty()) {
            toast("Delivery address is required")
            return
        }

        val orderReq = OrderPlaceReq()
        orderReq.UserID = profile.UserID
        orderReq.CustomerName = profile.Name
        orderReq.CustomerMobile = profile.MobileNo
        orderReq.CustomerEmail = profile.Email
        orderReq.DeliveryAddress = binding.edDeliveryAddress.value()
        orderReq.CustomerRemark = binding.edRemark.value()
        orderReq.RangeID = rangeId.toString()
        orderReq._itemList.addAll(cartItems)

        showAlert("Place order", "Are you sure you want to place an order?", "Yes", "No",true,
            object : OnOptionListener {
                override fun onClick(isYes: Boolean) {
                    if (isYes) {
                        ApiClient(this@AddressActivity).placeOrder(orderReq)
                            .observe(this@AddressActivity, {
                                if (it.response_code == ApiConstants.SUCCESS.toString()) {
                                    FoodDBHelper.clearCart(this@AddressActivity)

                                    showAlert(it.message, "", "OK", "",true,
                                        object : OnOptionListener {
                                            override fun onClick(isYes: Boolean) {
                                                if (isYes) {
                                                    val i = Intent(
                                                        this@AddressActivity,
                                                        MyOrdersActivity::class.java
                                                    )
                                                    i.putExtra(AppConstants.FROM_ORDER, true)
                                                    startActivity(i)
                                                    overridePendingTransition(
                                                        android.R.anim.fade_in,
                                                        android.R.anim.fade_out
                                                    )
                                                }
                                            }
                                        })
                                } else {
                                    toast(it.message)
                                }
                            })
                    }
                }
            })
    }

    private fun updateCart() {
        totalValue = 0
        FoodDBHelper.getCartItems(this, object : FoodDBHelper.RoomCallBack {
            override fun onItem(item: SubItem) {
                // do nothing
            }

            override fun onNotExist() {
                // do nothing
            }

            override fun onAllItems(items: ArrayList<SubItem>) {
                if (items.isNotEmpty()) {
                    cartItems.addAll(items)
                    for (item in items) {
                        totalValue += item.OrderQty * item.Price
                    }
                }
            }
        })
    }
}