package com.freshfoodz.room

import androidx.room.*
import com.freshfoodz.model.SubItem

@Dao
interface CartDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addItem(item: SubItem)

    @Update
    fun updateItem(item: SubItem)

    @get:Query("SELECT * FROM SubItem")
    val items: List<SubItem>

    @Query("SELECT * FROM SubItem WHERE ItemID=:itemId")
    fun getSubItems(itemId: Int): List<SubItem>

    @Query("SELECT * FROM SubItem WHERE StockID=:stockId")
    fun getProduct(stockId: Int): SubItem

    @Delete
    fun deleteItem(item: SubItem)

    @Query("DELETE FROM SubItem")
    fun clear()

    /*@Query("SELECT EXISTS(SELECT * FROM SubItem WHERE ItemID=(:itemId))")
    fun checkProduct(itemId: Int): Boolean*/

}