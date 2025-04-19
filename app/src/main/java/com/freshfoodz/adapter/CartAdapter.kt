package com.freshfoodz.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.freshfoodz.R
import com.freshfoodz.custom.ItemVariantView
import com.freshfoodz.databinding.ItemCartBinding
import com.freshfoodz.model.SubItem

class CartAdapter(private var updateListener: OnUpdateListener) :
    RecyclerView.Adapter<CartAdapter.StockViewHolder>() {

    private var items = arrayListOf<SubItem>()
    private lateinit var context: Context

    inner class StockViewHolder(val binding: ItemCartBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private var params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        private var subTotal = 0

        fun bindData(obj: SubItem) {

            binding.txtItemTotalPrice.visibility = View.VISIBLE

            binding.txtItemName.text = obj.ItemName

            binding.variantLayout.removeAllViews()
            binding.variantLayout.invalidate()

            binding.variantLayout.addView(
                ItemVariantView(context, obj,
                    object : ItemVariantView.OnUpdateListener {
                        override fun onUpdate(isDelete: Boolean) {
                            subTotal = obj.Price * obj.OrderQty
                            binding.txtItemTotalPrice.text = "$subTotal ₹"
                            updateListener.update(isDelete)
                        }
                    }), params
            )
            subTotal = obj.Price * obj.OrderQty
            binding.txtItemTotalPrice.text = "$subTotal ₹"
        }
    }

    fun setItems(newItems: ArrayList<SubItem>) {
        this.items = arrayListOf()
        this.items = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockViewHolder {
        val binding: ItemCartBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_cart,
            parent,
            false
        )
        context = parent.context
        return StockViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StockViewHolder, position: Int) {
        holder.bindData(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    interface OnUpdateListener {
        fun update(isDelete: Boolean)
    }
}