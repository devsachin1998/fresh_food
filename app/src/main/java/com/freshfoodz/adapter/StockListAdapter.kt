package com.freshfoodz.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.freshfoodz.R
import com.freshfoodz.custom.ItemVariantView
import com.freshfoodz.databinding.ItemFoodBinding
import com.freshfoodz.helper.load
import com.freshfoodz.model.StockListResponse
import java.util.*

class StockListAdapter(private var updateListener: OnUpdateListener) :
    RecyclerView.Adapter<StockListAdapter.StockViewHolder>() {

    private var items = arrayListOf<StockListResponse.Item>()
    private var filterItems = arrayListOf<StockListResponse.Item>()
    private lateinit var context: Context

    inner class StockViewHolder(val binding: ItemFoodBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private var params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        fun bindData(obj: StockListResponse.Item) {
            binding.txtItemTotalPrice.visibility = View.GONE

            binding.txtItemName.text = obj.ItemName
            binding.txtItemName.isSelected = true
            binding.imgItem.load(obj.ImagePath)

            binding.variantLayout.removeAllViews()
            binding.variantLayout.invalidate()

            for (subItem in obj._subitemList) {
                binding.variantLayout.addView(
                    ItemVariantView(context, subItem,
                        object : ItemVariantView.OnUpdateListener {
                            override fun onUpdate(isDelete: Boolean) {
                                updateListener.update()
                            }
                        }), params
                )
            }
        }
    }

    fun setItems(newItems: ArrayList<StockListResponse.Item>) {
        this.items = arrayListOf()
        this.filterItems = arrayListOf()
        this.items = newItems
        this.filterItems = newItems
        notifyDataSetChanged()
    }

    fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                filterItems = if (charString.isEmpty()) {
                    items
                } else {
                    val filteredList = arrayListOf<StockListResponse.Item>()
                    for (row in items) {

                        if (row.ItemName.lowercase(Locale.getDefault())
                                .contains(charString.lowercase(Locale.getDefault())) || row.ItemName.contains(
                                charSequence
                            )
                        ) {
                            filteredList.add(row)
                        }

                    }
                    filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = filterItems
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                filterItems = filterResults.values as ArrayList<StockListResponse.Item>
                notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockViewHolder {
        val binding: ItemFoodBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_food,
            parent,
            false
        )
        context = parent.context
        return StockViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StockViewHolder, position: Int) {
        holder.bindData(filterItems[position])
        if (position == filterItems.lastIndex) {
            holder.itemView.setPadding(0, 0, 0, 24)
        } else {
        holder.itemView.setPadding(0, 5, 0, 10)
        }
    }

    override fun getItemCount(): Int {
        return filterItems.size
    }

    interface OnUpdateListener {
        fun update()
    }
}