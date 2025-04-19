package com.freshfoodz.api

object ApiConstants {

    const val HOST = "http://api.freshfoodz.in.net/"

    private const val API_PREFIX = "api/Manage/"
    const val SUCCESS = 200
    const val STOCK_LIST = API_PREFIX + "GetStockItemList"
    const val LOGIN = API_PREFIX + "login"
    const val SIGN_UP = API_PREFIX + "AddUpdateUser"
    const val CHANGE_PWD = API_PREFIX + "ChangePassword"
    const val PLACE_ORDER = API_PREFIX + "PlaceOrder"
    const val ORDER_LIST = API_PREFIX + "GetUserOrderList"
    const val NOTIFICATION_LIST = API_PREFIX + "GetNotificationList"
    const val CancelOrder = API_PREFIX + "CancelOrder"

    const val WALLET_HISTORY = API_PREFIX + "GetWalletHistory"
    const val ADD_PAYMENT = API_PREFIX + "OnlinePayment"

    const val USER_DETAILS = API_PREFIX + "GetUserDetails"
    const val START_UP = API_PREFIX + "StartUp"
    const val ADD_MONEY = API_PREFIX + "AddBalance"
    const val DELIVERY_TIME = API_PREFIX + "GetDeliveryTimeRange"

    const val MEDIA_LIST = API_PREFIX + "media"
    const val ADD_SUGGESTION = API_PREFIX + "suggestion"
    const val RANDOM_WALLPAPER = API_PREFIX + "wallpaper/random"
}