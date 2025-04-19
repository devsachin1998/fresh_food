package com.freshfoodz.model

data class OrderPlaceReq(
    var CustomerAddress: String,
    var CustomerEmail: String,
    var CustomerMobile: String,
    var CustomerName: String,
    var CustomerRemark: String,
    var DeliveryAddress: String,
    var RangeID: String,
    var UserID: Int,
    var _itemList: ArrayList<SubItem>
) {

    constructor() : this("default", "", "", "", "", "", "",0, arrayListOf())

    data class Item(
        var ItemID: Int,
        var ItemName: String,
        var OrderQty: Int,
        var Price: Double
    ) {
        constructor() : this(0, "", 0, 0.0)
    }
}