package com.freshfoodz.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SubItem(
    var ItemID: Int,
    var ItemName: String,
    var MainID: Int,
    var Price: Int,

    @PrimaryKey
    var StockID: Int,
    var SubItemID: Int,
    var Weight: String,
    var OrderQty: Int
) {
    constructor() : this(0, "", 0, 0, 0, 0, "", 0)
}