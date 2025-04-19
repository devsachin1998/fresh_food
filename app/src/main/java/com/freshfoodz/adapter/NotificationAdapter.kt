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
import com.freshfoodz.databinding.ItemNotificationBinding
import com.freshfoodz.helper.AppConstants
import com.freshfoodz.helper.formatDate
import com.freshfoodz.helper.load
import com.freshfoodz.helper.openIntentUrl
import com.freshfoodz.model.NotificationResponse
import com.freshfoodz.model.OrderListRes
import com.freshfoodz.ui.MyOrdersDetailActivity


class NotificationAdapter : RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>() {

    private lateinit var context: Context
    private var items = arrayListOf<NotificationResponse.Notification>()

    inner class NotificationViewHolder(val binding: ItemNotificationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData(notification: NotificationResponse.Notification) {

            binding.txtTitle.text = notification.Tittle
            binding.txtDate.text = formatDate(notification.Date,"MM/dd/yyyy hh:mm:ss a",
                " dd/MM/yyyy hh:mm:ss a")
            binding.txtDescription.text = notification.Message
            if (notification.Image!="")
            {
                binding.imageBanner.visibility=View.VISIBLE;
                binding.imageBanner.load(notification.Image)

            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val binding: ItemNotificationBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_notification,
            parent,
            false
        )
        context = parent.context
        return NotificationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        holder.bindData(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setItems(newItems: ArrayList<NotificationResponse.Notification>) {
        this.items = arrayListOf()
        this.items = newItems
        notifyDataSetChanged()
    }
}