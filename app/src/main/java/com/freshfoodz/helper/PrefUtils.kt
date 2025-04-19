package com.freshfoodz.helper

import android.content.Context
import com.freshfoodz.helper.Prefs.Companion.with
import com.freshfoodz.model.LoginRes
import com.freshfoodz.model.StartUpResponse
import com.freshfoodz.model.StockListResponse
import com.google.gson.Gson

object PrefUtils {

    const val IS_LOGGED_IN = "is_logged_in"
    const val USER_PROFILE = "user_profile"
    const val START_UP = "start_up"
    const val item_all = "item_all"

    fun saveBoolean(context: Context, key: String, value: Boolean) {
        with(context)!!.save(key, value)
    }

    fun getBoolean(context: Context, key: String): Boolean {
        return with(context)!!.getBoolean(key, false)
    }
    @JvmStatic
    fun  saveStringValue(context: Context, key: String, value: String) {
        with(context)!!.save(key, value)
    }
    @JvmStatic
    fun getStringValue(context: Context, key: String): String {
        return with(context)!!.getString(key, "").toString()
    }

    fun getProfile(context: Context): LoginRes.UserProfile {
        val profile = with(context)!!.getString(USER_PROFILE, "")
        return MyApplication.gson!!.fromJson(profile, LoginRes.UserProfile::class.java)
    }

    fun getStartUp(context: Context): StartUpResponse.StartUp {
        val profile = with(context)!!.getString(START_UP, "")
        return MyApplication.gson!!.fromJson(profile, StartUpResponse.StartUp::class.java)
    }
    fun getitem(context: Context): Array<StockListResponse.Item>? {
        val emptyList = Gson().toJson(ArrayList<StockListResponse.Item>())

        val profile  = with(context)!!.getString(item_all,"[]")
       // val enums: Array<ChannelSearchEnum> =
        return  MyApplication.gson!!.fromJson(
            profile,
            Array<StockListResponse.Item>::class.java
        )

      //  return MyApplication.gson!!.fromJson(profile, StockListResponse.Item::class.java)
    }

    fun saveObjectValue(context: Context, key: String, data: Any?) {
        val toJson = MyApplication.gson!!.toJson(data)
        saveStringValue(context, key, toJson)
    }

    /*fun setFirstTimeWallpaper(context: Context?, isFirstTime: Boolean) {
        with(context)!!.save(FIRST_TIME_WALLPAPER, isFirstTime)
    }

    fun getFirstTimeWallpaper(context: Context?): Boolean {
        return with(context)!!.getBoolean(FIRST_TIME_WALLPAPER, true)
    }

    fun setFirstTimeFilter(context: Context?, isFirstTime: Boolean) {
        with(context)!!.save(FIRST_TIME_FILTER, isFirstTime)
    }

    fun getFirstTimeFilter(context: Context?): Boolean {
        return with(context)!!.getBoolean(FIRST_TIME_FILTER, true)
    }

    fun setRating(ctx: Context?, value: Int) {
        with(ctx)!!.save(RATING_COUNT, value)
    }

    fun getRating(ctx: Context?): Int {
        return with(ctx)!!.getInt(RATING_COUNT, 0)
    }

    fun setTheme(ctx: Context?, value: Int) {
        with(ctx)!!.save(THEME, value)
    }

    fun getTheme(ctx: Context?): Int {
        return with(ctx)!!.getInt(THEME, AppConstants.LIGHT)
    }

    fun setCount(ctx: Context?, value: Int) {
        with(ctx)!!.save(AD_COUNT, value)
    }

    fun getCount(ctx: Context?): Int {
        return with(ctx)!!.getInt(AD_COUNT, 0)
    }

    fun setRingCount(ctx: Context?, value: Int) {
        with(ctx)!!.save(RING_COUNT, value)
    }

    fun getRingCount(ctx: Context?): Int {
        return with(ctx)!!.getInt(RING_COUNT, 0)
    }

    fun setPageCount(ctx: Context?, value: Int) {
        with(ctx)!!.save(PAGE_COUNT, value)
    }

    fun getPageCount(ctx: Context?): Int {
        return with(ctx)!!.getInt(PAGE_COUNT, 0)
    }*/
}