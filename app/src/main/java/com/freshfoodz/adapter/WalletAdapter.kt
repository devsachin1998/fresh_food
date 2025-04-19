package com.freshfoodz.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.freshfoodz.R
import com.freshfoodz.databinding.ItemWalletBinding
import com.freshfoodz.model.WalletHistoryResponse
import java.text.DecimalFormat
import kotlin.math.abs

class WalletAdapter() :
    RecyclerView.Adapter<WalletAdapter.WalletViewHolder>() {

    private var categories = arrayListOf<WalletHistoryResponse.Wallet.History>()
    private lateinit var context: Context

    inner class WalletViewHolder(val binding: ItemWalletBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData(obj: WalletHistoryResponse.Wallet.History) {

            val date: List<String> = obj.newEntryDate.split(",");
            binding.txtDate.text = date[0]
          //  binding.txttransAmt.text = "${obj.newAmount} ₹"
           binding.txtAmount.text = "${obj.newAmount} ₹"
            var bal=0.0




//            binding.txttransAmt.text = "${DecimalFormat("0.##").format(abs(obj.Balance))} ₹"
            if (obj.Type.equals("Credit", true)) {
               //binding.txtAmount.setTextColor(ContextCompat.getColor(context, R.color.green))
               binding.txtAmount.setTextColor(ContextCompat.getColor(context, R.color.green))
                binding.txttypename.setText("Paid-Cash")
                binding.txttranstype.setText("Payment Id : "+obj.HistoryID)



            } else {
                // binding.txtAmount.setTextColor(ContextCompat.getColor(context, R.color.red))
                binding.txtAmount.setTextColor(ContextCompat.getColor(context, R.color.red))

                binding.txttypename.setText("Purchase")
                binding.txttranstype.setText("Order Id : " + obj.OrderNo)
            }
        }
    }

    fun setItems(newItems: ArrayList<WalletHistoryResponse.Wallet.History>) {
        this.categories = arrayListOf()
        this.categories = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WalletViewHolder {
        val binding: ItemWalletBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_wallet,
            parent,
            false
        )
        context = parent.context
        return WalletViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WalletViewHolder, position: Int) {
        holder.bindData(categories[position])
    }

    override fun getItemCount(): Int {
        return categories.size
    }
}