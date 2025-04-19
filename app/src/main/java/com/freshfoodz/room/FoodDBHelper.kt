package com.freshfoodz.room

import android.content.Context
import com.freshfoodz.model.StockListResponse
import com.freshfoodz.model.SubItem

object FoodDBHelper {

    fun addItem(context: Context, item: SubItem) {
        AppExecutors.instance?.diskIO()?.execute {
            FoodDatabase.getInstance(context)?.cartDao()?.addItem(item)
        }
    }

    fun updateItem(context: Context, item: SubItem) {
        AppExecutors.instance?.diskIO()?.execute {
            FoodDatabase.getInstance(context)?.cartDao()?.updateItem(item)
        }
    }

    fun deleteItem(context: Context, item: SubItem) {
        AppExecutors.instance?.diskIO()?.execute {
            FoodDatabase.getInstance(context)?.cartDao()?.deleteItem(item)
        }
    }

    fun getItem(context: Context, item: SubItem, callBack: RoomCallBack) {
        AppExecutors.instance?.diskIO()?.execute {
            val item = FoodDatabase.getInstance(context)?.cartDao()?.getProduct(item.StockID)
            if (item != null) {
                AppExecutors.instance?.mainThread()?.execute {
                    callBack.onItem(item)
                }
            } else {
                AppExecutors.instance?.mainThread()?.execute {
                    callBack.onNotExist()
                }
            }

        }
    }

    fun getCartItems(context: Context, callBack: RoomCallBack) {
        AppExecutors.instance?.diskIO()?.execute {
            val items = FoodDatabase.getInstance(context)?.cartDao()?.items
            if (items == null || items.isEmpty()) {
                AppExecutors.instance?.mainThread()?.execute {
                    callBack.onNotExist()
                }
            } else {
                AppExecutors.instance?.mainThread()?.execute {
                    callBack.onAllItems(items as ArrayList<SubItem>)
                }
            }
        }
    }

    fun getSubItems(context: Context, item: StockListResponse.Item, callBack: RoomCallBack) {
        AppExecutors.instance?.diskIO()?.execute {
            val items = FoodDatabase.getInstance(context)?.cartDao()?.getSubItems(item.ItemID)
            if (items == null || items.isEmpty()) {
                AppExecutors.instance?.mainThread()?.execute {
                    callBack.onNotExist()
                }
            } else {
                AppExecutors.instance?.mainThread()?.execute {
                    callBack.onAllItems(items as ArrayList<SubItem>)
                }
            }
        }
    }

    fun clearCart(context: Context) {
        AppExecutors.instance?.diskIO()?.execute {
            FoodDatabase.getInstance(context)?.cartDao()?.clear()
        }
    }

    interface RoomCallBack {
        fun onItem(item: SubItem)
        fun onNotExist()
        fun onAllItems(items: ArrayList<SubItem>)
    }

}