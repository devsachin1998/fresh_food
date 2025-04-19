package com.freshfoodz.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.freshfoodz.R
import com.freshfoodz.adapter.CartAdapter
import com.freshfoodz.databinding.ActivityCartBinding
import com.freshfoodz.helper.closeScreen
import com.freshfoodz.helper.fireIntent
import com.freshfoodz.model.SubItem
import com.freshfoodz.room.FoodDBHelper

class CartActivity : AppCompatActivity(), CartAdapter.OnUpdateListener {

    private lateinit var binding: ActivityCartBinding
    private var cartItems = arrayListOf<SubItem>()
    private lateinit var adapter: CartAdapter
    private var totalValue = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cart)
        init()
    }

    private fun init() {
        setSupportActionBar(binding.toolbarLayout.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbarLayout.toolbar.title = "Cart"

        adapter = CartAdapter(this)
        binding.rvItems.adapter = adapter

        updateCart(true)

        initClick()

    }

    private fun updateCart(isUpdate: Boolean) {
        totalValue = 0
        FoodDBHelper.getCartItems(this, object : FoodDBHelper.RoomCallBack {
            override fun onItem(item: SubItem) {
                // do nothing
            }

            override fun onNotExist() {
                binding.layoutPlace.visibility = View.GONE
                binding.imgEmpty.visibility = View.VISIBLE
                adapter.setItems(arrayListOf())
            }

            override fun onAllItems(items: ArrayList<SubItem>) {
                if (items.isNotEmpty()) {
                    binding.layoutPlace.visibility = View.VISIBLE
                    binding.imgEmpty.visibility = View.GONE
                    if (isUpdate) {
                        cartItems.addAll(items)
                        adapter.setItems(items)
                    }
                    for (item in items) {
                        totalValue += item.OrderQty * item.Price
                    }
                    binding.txtTotalPrice.text = "$totalValue ₹"
                } else {
                    binding.layoutPlace.visibility = View.GONE
                    binding.imgEmpty.visibility = View.VISIBLE
                    adapter.setItems(arrayListOf())
                }
            }
        })
    }

    private fun initClick() {
        binding.toolbarLayout.toolbar.setNavigationOnClickListener { closeScreen() }
        binding.layoutPlace.setOnClickListener { fireIntent(AddressActivity::class.java) }
    }

    override fun onBackPressed() {
        closeScreen()
    }

    override fun update(isDelete: Boolean) {
        updateCart(isDelete)
    }
}