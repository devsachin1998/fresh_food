package com.freshfoodz.helper

import android.content.Context
import android.net.ConnectivityManager

object Functions {


    @JvmStatic
    fun isConnected(context: Context): Boolean {
        val connectivityManager =
            (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}