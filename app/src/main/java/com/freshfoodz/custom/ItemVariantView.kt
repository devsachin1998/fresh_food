package com.freshfoodz.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.freshfoodz.databinding.ItemVariantBinding
import com.freshfoodz.model.SubItem
import com.freshfoodz.room.FoodDBHelper

class ItemVariantView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    constructor(context: Context, obj: SubItem, listener: OnUpdateListener) : this(context) {
        variant = obj
        this.listener = listener
        initView()
    }

    private lateinit var listener: OnUpdateListener
    private lateinit var binding: ItemVariantBinding
    private var variant = SubItem()

    private fun initView() {
        binding = ItemVariantBinding.inflate(LayoutInflater.from(context), this, true)

        binding.txtItemRate.text = "${variant.Price} ₹"
        binding.txtItemUnit.text = variant.Weight

        FoodDBHelper.getItem(context, variant, object : FoodDBHelper.RoomCallBack {
            override fun onItem(item: SubItem) {
                variant.OrderQty = item.OrderQty
                updateView()
            }

            override fun onNotExist() {
                variant.OrderQty = 0
                updateView()
            }

            override fun onAllItems(items: ArrayList<SubItem>) {}
        })

        binding.txtAdd.setOnClickListener {
            variant.OrderQty = 1
            FoodDBHelper.addItem(context, variant)
            updateView()
            listener.onUpdate(false)
        }

        binding.btnPlus.setOnClickListener {
            variant.OrderQty++
            FoodDBHelper.addItem(context, variant)
            updateView()
            listener.onUpdate(false)
        }

        binding.btnMinus.setOnClickListener {
            if (variant.OrderQty > 1) {
                variant.OrderQty--
                FoodDBHelper.addItem(context, variant)
                listener.onUpdate(false)
            } else {
                variant.OrderQty = 0
                FoodDBHelper.deleteItem(context, variant)
                listener.onUpdate(true)
            }
            updateView()
        }
    }

    private fun updateView() {
        if (variant.OrderQty > 0) {
            binding.txtAdd.visibility = View.GONE
            binding.layoutQty.visibility = View.VISIBLE
            binding.txtQty.text = variant.OrderQty.toString()
        } else {
            binding.txtAdd.visibility = View.VISIBLE
            binding.layoutQty.visibility = View.GONE
        }
    }

    interface OnUpdateListener {
        fun onUpdate(isDelete: Boolean)
    }
}
