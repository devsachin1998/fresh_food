package com.freshfoodz.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.freshfoodz.R
import com.freshfoodz.databinding.ItemOrderBinding
import com.freshfoodz.helper.AppConstants
import com.freshfoodz.helper.openIntentUrl
import com.freshfoodz.model.OrderListRes
import com.freshfoodz.ui.MyOrdersDetailActivity


class OrderAdapter : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    private lateinit var context: Context
    private var items = arrayListOf<OrderListRes.Order>()

    inner class OrderViewHolder(val binding: ItemOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData(order: OrderListRes.Order) {
            binding.txtOrderNumber.text = "Order No. ${order.OrderNo}"

            binding.txtOrderAmount.text = "Rs. ${order.newTotal}"

            binding.txStatus.text = order.OrderStatus
            binding.txtDate.text = order.newOrderDate

            if (order.OrderStatus.equals(AppConstants.DISPATCHED, true)) {
                binding.imgReceipt.visibility = View.VISIBLE
            } else {
                binding.imgReceipt.visibility = View.GONE
            }
            binding.root.setOnClickListener {
                val intent = Intent(context, MyOrdersDetailActivity::class.java);
                intent.putExtra("OrderNo", order.OrderNo);
                intent.putExtra("Date", order.newOrderDate);
                intent.putExtra("Time", order.DeliveryTime);
                intent.putExtra("Status", order.OrderStatus);
                intent.putExtra("Price", order.newTotal);

                intent.putExtra("Index", position);
               // intent.putExtra("extra_object", order._itemList)

                context.startActivity(intent);
            }
            var date = ""
            var time = ""

//            when (order.OrderStatus) {
//                AppConstants.DELIVERED -> {
//                    if (TextUtils.isEmpty(order.DeliveryDate) && TextUtils.isEmpty(order.DeliveryTime)) {
//                        binding.txtExpectedDelivery.visibility = View.GONE
//
//                    } else {
//                        binding.txtExpectedDelivery.visibility = View.VISIBLE
//                        binding.txtExpectedDelivery.setCompoundDrawablesRelativeWithIntrinsicBounds(
//                            0,
//                            0,
//                            0,
//                            0
//                        )
//                        if (!TextUtils.isEmpty(order.DeliveryDate)) {
//                            date = order.DeliveryDate
//                        }
//                        if (!TextUtils.isEmpty(order.DeliveryTime)) {
//                            time = order.DeliveryTime
//                        }
////                        if (TextUtils.isEmpty(time)) {
////                            binding.txtExpectedDelivery.text =
////                                "Order has been delivered on ${
////                                    formatDate(
////                                        date,
////                                        "MM/dd/yyyy",
////                                        "dd MMM yyyy"
////                                    )
////                                }"
////
////                        } else {
////
////                            binding.txtExpectedDelivery.text =
////                                "Order has been delivered on ${
////                                    formatDate(
////                                        date,
////                                        "MM/dd/yyyy",
////                                        "dd MMM yyyy"
////                                    )
////                                } $time"
////                        }
//                    }
//                }
////                AppConstants.READY -> {
////                    if (TextUtils.isEmpty(order.DeliveryDate) && TextUtils.isEmpty(order.DeliveryTime)) {
////                        binding.txtExpectedDelivery.visibility = View.GONE
////
////                    } else {
////                        binding.txtExpectedDelivery.setCompoundDrawablesRelativeWithIntrinsicBounds(
////                            R.drawable.ic_delivery,
////                            0,
////                            0,
////                            0
////                        )
////                        binding.txtExpectedDelivery.visibility = View.VISIBLE
////                        if (!TextUtils.isEmpty(order.DeliveryDate)) {
////                            date = order.DeliveryDate
////                        }
////                        if (!TextUtils.isEmpty(order.DeliveryTime)) {
////                            time = order.DeliveryTime
////                        }
//////                        if (TextUtils.isEmpty(time)) {
//////                            binding.txtExpectedDelivery.text =
//////                                "Estimate delivery by ${
//////                                    formatDate(
//////                                        date,
//////                                        "MM/dd/yyyy",
//////                                        "dd MMM yyyy"
//////                                    )
//////                                }"
//////
//////                        } else {
//////
//////                            binding.txtExpectedDelivery.text =
//////                                "Estimate delivery by ${
//////                                    formatDate(
//////                                        date,
//////                                        "MM/dd/yyyy",
//////                                        "dd MMM yyyy"
//////                                    )
//////                                } $time"
//////                        }
////                    }
////                }
////                AppConstants.ACCEPTED -> {
////                    binding.txtExpectedDelivery.visibility = View.GONE
////                }
//            }

            binding.imgReceipt.setOnClickListener { context.openIntentUrl("https://api.freshfoodz.in/Content/OrderSummary/OrderSummary_" + order.OrderNo + ".pdf") }

            binding.itemLayout.removeAllViews()
            binding.itemLayout.invalidate()

            for (row in order._itemList) {
                val textView = TextView(context)
                textView.text = "${row.ItemName} - ${row.OrderQty.toInt()} x ${row.Weight}"
                binding.itemLayout.addView(textView)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding: ItemOrderBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_order,
            parent,
            false
        )
        context = parent.context
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bindData(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setItems(newItems: ArrayList<OrderListRes.Order>) {
        this.items = arrayListOf()
        this.items = newItems
        notifyDataSetChanged()
    }
}