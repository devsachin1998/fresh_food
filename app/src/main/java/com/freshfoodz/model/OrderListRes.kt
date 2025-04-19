package com.freshfoodz.model

import com.freshfoodz.helper.formatDate
import java.text.DecimalFormat

data class OrderListRes(
    var obj: ArrayList<Order>
) : BaseResponse() {

    constructor() : this(arrayListOf())

    data class Order(
        var AdminRemark: String,
        var CustomerAddress: String,
        var CustomerEmail: String,
        var CustomerMobile: String,
        var CustomerName: String,
        var CustomerRemark: String,
        var DeliveryAddress: String,
        var DeliveryDate: String,
        var NoofItem: Int,
        var OrderDate: String,
        var OrderID: Int,
        var OrderNo: String,
        var OrderStatus: String,
        var PaidAmount: Any,
        var PaymentDate: String,
        var PaymentStatus: String,
        var DeliveryTime: String,
        var Total: Double,
        var UserID: Int,
        var _itemList: ArrayList<OrderItem>
    ) {
        val newOrderDate: String
            get() = formatDate(
                OrderDate,
                "MM/dd/yyyy hh:mm:ss a",
                "dd MMM yyyy, hh:mm a"
            )

        val newTotal: String
            get() = DecimalFormat("0.##").format(Total)

        data class OrderItem(
            var ItemID: Int,
            var ItemName: String,
            var OrderID: Int,
            var OrderItemID: Int,
            var OrderNo: String,
            var Weight: String,
            var OrderQty: Double,
            var Price: Double,
            var ImagePath:String,

            )
    }
}