package com.freshfoodz.model


import com.freshfoodz.ui.PaymentStatus
import java.text.DecimalFormat

data class NotificationResponse(
    var obj:  ArrayList<Notification>
) : BaseResponse() {

    constructor() : this(arrayListOf())

    data class Notification(
        var Tittle: String,
        var Image: String,
        var Message: String,
        var Date: String,
    )
}
