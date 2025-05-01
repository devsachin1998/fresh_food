package com.freshfoodz.model

data class StartUpResponse(
    var obj: StartUp
) : BaseResponse() {

    constructor() : this(StartUp())

    data class StartUp(
        var HelpLineNo: String,
        var holidaytext: String,
        var Version: String,
        var _categoryList: ArrayList<Category>
    ) {

        constructor() : this("","dasdasdas", "",arrayListOf())

        data class Category(
            var CategoryID: Int,
            var CategoryName: String,
            var selected: Boolean
        )
    }
}