package com.freshfoodz.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.freshfoodz.R
import com.freshfoodz.databinding.ItemCategoryBinding
import com.freshfoodz.model.StartUpResponse
import java.util.*

class CategoryAdapter(
    private var categories: ArrayList<StartUpResponse.StartUp.Category>,
    private var listener: OnCategorySelectionListener
) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private lateinit var context: Context
    private  var selectedpos=0
    inner class CategoryViewHolder(val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData(obj: StartUpResponse.StartUp.Category,pos:Int) {

            binding.txtItemName.text = obj.CategoryName
            binding.txtItemName.isSelected = true
            if(selectedpos==pos)
            {
                binding.childView.background =
                    ContextCompat.getDrawable(context, R.drawable.drawable_category_selected)
                binding.txtItemName.setTextColor(ContextCompat.getColor(context, R.color.white))
                categories[selectedpos].selected=true

            }
            else
            {
                binding.childView.background =
                    ContextCompat.getDrawable(context, R.drawable.drawable_category_deselected)
                binding.txtItemName.setTextColor(ContextCompat.getColor(context, R.color.black))
            }

//            if (obj.selected) {
//                binding.childView.background =
//                    ContextCompat.getDrawable(context, R.drawable.drawable_category_selected)
//                binding.txtItemName.setTextColor(ContextCompat.getColor(context, R.color.white))
//            } else {
//                binding.childView.background =
//                    ContextCompat.getDrawable(context, R.drawable.drawable_category_deselected)
//                binding.txtItemName.setTextColor(ContextCompat.getColor(context, R.color.black))
//            }

            binding.root.setOnClickListener {
                if(position==pos)
                {
                    binding.childView.background =
                        ContextCompat.getDrawable(context, R.drawable.drawable_category_selected)
                    binding.txtItemName.setTextColor(ContextCompat.getColor(context, R.color.white))
                    selectedpos=pos

                    categories[selectedpos].selected=true
                    for (cat in categories) {
                        cat.selected=false

                    }
                    notifyDataSetChanged()
                }
                else
                {
                    binding.childView.background =
                        ContextCompat.getDrawable(context, R.drawable.drawable_category_deselected)
                    binding.txtItemName.setTextColor(ContextCompat.getColor(context, R.color.black))
                }
//                obj.selected = !obj.selected
//                if (obj.selected) {
//                    binding.childView.background =
//                        ContextCompat.getDrawable(context, R.drawable.drawable_category_selected)
//                    binding.txtItemName.setTextColor(ContextCompat.getColor(context, R.color.white))
//                } else {
//                    binding.childView.background =
//                        ContextCompat.getDrawable(context, R.drawable.drawable_category_deselected)
//                    binding.txtItemName.setTextColor(ContextCompat.getColor(context, R.color.black))
//                }
//
//                val selectedIds = arrayListOf<Int>()
//                for (cat in categories) {
//                    if (cat.selected) {
//                        selectedIds.add(cat.CategoryID)
//                    }
//                }
//                listener.getSelected(selectedIds)
            }
            val selectedIds = arrayListOf<Int>()
                for (cat in categories) {
                    if (cat.selected) {
                        selectedIds.add(cat.CategoryID)
                    }
                }
                listener.getSelected(selectedIds)
        }
    }

    fun setItems(newItems: ArrayList<StartUpResponse.StartUp.Category>) {
        this.categories = arrayListOf()
        this.categories = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding: ItemCategoryBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_category,
            parent,
            false
        )
        context = parent.context
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bindData(categories[position],position)
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    interface OnCategorySelectionListener {
        fun getSelected(categories: ArrayList<Int>)
    }
}