package com.freshfoodz.model

data class RegisterUpdateReq(
    var Email: String,
    var MobileNo: String,
    var Name: String,
    var Password: String,
    var RoleID: Int,
    var UserID: Int,
    var UserName: String,
    var Address: String
) {
    constructor() : this("", "", "", "", 4, 0, "", "")
}