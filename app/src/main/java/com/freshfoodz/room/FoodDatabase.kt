package com.freshfoodz.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.freshfoodz.model.StockListResponse
import com.freshfoodz.model.SubItem

@Database(entities = [StockListResponse.Item::class, SubItem::class], version = 2)
@TypeConverters(FoodConverters::class)
abstract class FoodDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao?

    companion object {
        private const val DB_NAME = "food_db"
        private var instance: FoodDatabase? = null

        @JvmStatic
        @Synchronized
        fun getInstance(context: Context?): FoodDatabase? {
            if (instance == null) {
                instance = Room.databaseBuilder(context!!, FoodDatabase::class.java, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance
        }
    }
}