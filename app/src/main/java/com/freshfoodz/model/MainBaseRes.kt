package com.freshfoodz.model

data class MainBaseRes(
    var obj: String
) : BaseResponse() {
    constructor() : this("")
}