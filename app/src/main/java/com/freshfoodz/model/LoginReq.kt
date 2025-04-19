package com.freshfoodz.model

data class LoginReq(
    var Password: String,
    var UserName: String,
    var fcmtoken : String,
    var Version  : String
)