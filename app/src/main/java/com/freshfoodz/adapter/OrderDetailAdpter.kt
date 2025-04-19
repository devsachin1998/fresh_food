package com.freshfoodz.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.freshfoodz.R
import com.freshfoodz.databinding.ItemOrderdetailBinding
import com.freshfoodz.helper.load
import com.freshfoodz.model.OrderListRes

class OrderDetailAdpter : RecyclerView.Adapter<OrderDetailAdpter.OrderViewHolder>() {

    private lateinit var context: Context
    private var items = arrayListOf<OrderListRes.Order.OrderItem>()

    inner class OrderViewHolder(val binding: ItemOrderdetailBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData(order: OrderListRes.Order.OrderItem) {
            binding.txtName.text = order.ItemName
            binding.textPrice.text = order.Price.toString()
            binding.textGram.text = order.Weight
            binding.textView10.text = ""+order.OrderQty.toInt()
            binding.imgProduct.load(order.ImagePath)
//            binding.txtOrderAmount.text = "Rs. ${order.newTotal} ₹"
//
//            binding.txStatus.text = order.OrderStatus
//            binding.txtDate.text = order.newOrderDate
//
//            if (order.OrderStatus.equals(AppConstants.DISPATCHED, true)) {
//                binding.imgReceipt.visibility = View.VISIBLE
//            } else {
//                binding.imgReceipt.visibility = View.GONE
//            }
//
//            var date = ""
//            var time = ""

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

//            binding.imgReceipt.setOnClickListener { context.openIntentUrl("https://api.freshfoodz.in/Content/OrderSummary/OrderSummary_" + order.OrderNo + ".pdf") }
//
//            binding.itemLayout.removeAllViews()
//            binding.itemLayout.invalidate()

//            for (row in order._itemList) {
//                val item_name = TextView(context)
//                val item_price = TextView(context)
//                val item_weight = TextView(context)
//                val imageView = ImageView(context)
//                imageView.layoutParams = LinearLayout.LayoutParams(160, 160)
//                imageView.load(row.Imagepath)
//
//                item_name.text = "${row.ItemName} - ${row.OrderQty.toInt()} x ${row.Weight}"
//                binding.itemLayout.addView(item_name)
//                binding.itemLayout.addView(imageView)
//            }
//            binding.root.setOnClickListener {
//                val intent = Intent(context, MyOrdersDetailActivity::class.java);
//                intent.putExtra("orderdetail", "1");
//                context.startActivity(intent);
//            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding: ItemOrderdetailBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_orderdetail,
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

    fun setItems(newItems: ArrayList<OrderListRes.Order.OrderItem>) {
        this.items = arrayListOf()
        this.items = newItems
        notifyDataSetChanged()
    }
}