package com.freshfoodz.model

import com.freshfoodz.helper.formatDate
import java.text.DecimalFormat
import kotlin.math.abs

data class WalletHistoryResponse(
    var obj: Wallet
) : BaseResponse() {

    constructor() : this(Wallet())

    data class Wallet(
        var Balance: Double,
        var _historyList: ArrayList<History>
    ) {

        constructor() : this(0.0, arrayListOf())

        val newBalance: String
            get() = DecimalFormat("0.##").format(Balance)

        data class History(
            var Amount: Double,
            var EntryDate: String,
            var HistoryID: Int,
            var OrderNo: String,
            var Type: String,
            var UserID: Int,
            var WalletID: Int,
            var Balance : Double,

        ) {

            val newEntryDate: String
                get() = formatDate(
                    EntryDate,
                    "MM/dd/yyyy hh:mm:ss a",
                    "dd MMM yyyy, hh:mm a"
                )

            val newAmount: String
                get() = DecimalFormat("0.##").format(abs(Amount))
        }
    }
}