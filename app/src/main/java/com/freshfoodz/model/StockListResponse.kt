package com.freshfoodz.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.freshfoodz.room.FoodConverters

data class StockListResponse(
    var obj: ArrayList<Item>
) : BaseResponse() {

    constructor() : this(arrayListOf())

    @Entity
    data class Item(
        var CategoryID: Int,
        var CategoryName: String,
        var ImagePath: String,

        @PrimaryKey(autoGenerate = true)
        var ItemID: Int,

        var ItemName: String,

        @TypeConverters(FoodConverters::class)
        var _subitemList: ArrayList<SubItem>
    ) {
        constructor() : this(0, "", "", 0, "", arrayListOf())
    }

}