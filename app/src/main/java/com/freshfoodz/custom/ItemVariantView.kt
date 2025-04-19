package com.freshfoodz.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.freshfoodz.databinding.ItemVariantBinding
import com.freshfoodz.model.SubItem
import com.freshfoodz.room.FoodDBHelper
import me.himanshusoni.quantityview.QuantityView

class ItemVariantView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    constructor(context: Context, obj: SubItem, listener: OnUpdateListener) : this(
        context
    ) {
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
                binding.qtyView.quantity = item.OrderQty
                binding.qtyView.visibility = View.VISIBLE
                binding.txtAdd.visibility = View.GONE
            }

            override fun onNotExist() {
                binding.qtyView.quantity = 0
                binding.qtyView.visibility = View.GONE
                binding.txtAdd.visibility = View.VISIBLE
            }

            override fun onAllItems(items: ArrayList<SubItem>) {
                // do nothing
            }
        })

        binding.txtAdd.setOnClickListener {
            addSingleItem(variant)
        }

        binding.qtyView.onQuantityChangeListener =
            object : QuantityView.OnQuantityChangeListener {
                override fun onQuantityChanged(
                    oldQuantity: Int,
                    newQuantity: Int,
                    programmatically: Boolean
                ) {
                    if (newQuantity == 0) {
                        removeItem(variant)
                    } else {
                        variant.OrderQty = newQuantity
                        FoodDBHelper.addItem(context, variant)
                        listener.onUpdate(false)
                    }
                }

                override fun onLimitReached() {

                }
            }
    }

    private fun removeItem(variant: SubItem) {
        binding.qtyView.quantity = 0
        binding.qtyView.visibility = View.GONE
        binding.txtAdd.visibility = View.VISIBLE

        FoodDBHelper.deleteItem(context, variant)
        listener.onUpdate(true)
    }

    private fun addSingleItem(variant: SubItem) {
        binding.qtyView.quantity = 1
        binding.qtyView.visibility = View.VISIBLE
        binding.txtAdd.visibility = View.GONE

        variant.OrderQty = 1
        FoodDBHelper.addItem(context, variant)

        listener.onUpdate(false)

    }

    interface OnUpdateListener {
        fun onUpdate(isDelete: Boolean)
    }
}