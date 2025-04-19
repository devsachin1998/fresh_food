package com.freshfoodz.model

import java.text.DecimalFormat

data class LoginRes(
    var obj: UserProfile
) : BaseResponse() {

    constructor() : this(UserProfile())

    data class UserProfile(
        var Email: String,
        var IsActive: Boolean,
        var MobileNo: String,
        var Name: String,
        var Password: String,
        var RoleID: Int,
        var UserID: Int,
        var UserName: String,
        var Address: String,
        var WalletID: Int,
        var WalletBalance: Double,
    ) {

        val newWalletBalance: String
            get() = DecimalFormat("0.##").format(WalletBalance)

        constructor() : this("", false, "", "", "", 0, 0, "", "", 0, 0.0)
    }
}