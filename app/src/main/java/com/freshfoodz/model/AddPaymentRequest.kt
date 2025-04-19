package com.freshfoodz.model

class AddPaymentRequest (
    var TransactionId: Int,
    var UserID: Int,
    var UserName: String,
    var Amount: Double,
    var PaymentMode: String,
    var PaymentStatus: String,

)
