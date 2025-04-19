package com.freshfoodz.model


import com.freshfoodz.ui.PaymentStatus
import java.text.DecimalFormat

data class PaymentResponse(
    var obj: PaymentReq
) : BaseResponse() {

    constructor() : this(PaymentReq())

    data class PaymentReq(

        var PaymentStatus: String,
        var Balance: Double,
    ) {


        constructor() : this("",0.0)
    }
}
