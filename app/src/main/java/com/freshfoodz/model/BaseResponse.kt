package com.freshfoodz.model

abstract class BaseResponse(
    var message: String,
    var response_code: String,
    var token: String
) {
    constructor() : this("", "", "")
}