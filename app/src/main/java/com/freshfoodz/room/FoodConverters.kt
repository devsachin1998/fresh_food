package com.freshfoodz.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.freshfoodz.model.SubItem
import java.util.*

class FoodConverters {

    @TypeConverter
    fun storedStringToMyObjects(data: String?): ArrayList<SubItem> {
        val gson = Gson()
        if (data == null) {
            return arrayListOf()
        }
        val listType = object : TypeToken<ArrayList<SubItem>>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun myObjectsToStoredString(myObjects: ArrayList<SubItem>): String {
        val gson = Gson()
        return gson.toJson(myObjects)
    }
}