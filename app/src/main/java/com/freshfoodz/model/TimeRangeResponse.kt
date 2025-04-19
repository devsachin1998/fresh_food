package com.freshfoodz.model

data class TimeRangeResponse(
    var obj: ArrayList<Range>
) : BaseResponse() {

    constructor() : this(arrayListOf())

    data class Range(
        var Range: String,
        var RangeID: Int
    ) {
        constructor() : this("", 0)
    }
}