package com.freshfoodz.model

data class AddMoneyRequest(
    var Amount: Int,
    var UserID: Int,
    var WalletID: Int
)