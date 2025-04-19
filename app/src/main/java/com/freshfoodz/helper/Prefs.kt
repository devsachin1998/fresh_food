package com.freshfoodz.helper

import android.content.Context
import android.content.SharedPreferences

class Prefs private constructor(context: Context) {
    fun getString(key: String?, defValue: String?): String? {
        return preferences.getString(key, defValue)
    }

    fun save(key: String?, value: String?) {
        editor.putString(key, value).apply()
    }

    fun save(key: String?, value: Int) {
        editor.putInt(key, value).apply()
    }

    fun getInt(key: String?, defValue: Int): Int {
        return preferences.getInt(key, defValue)
    }

    fun save(key: String?, value: Boolean) {
        editor.putBoolean(key, value).apply()
    }

    fun getBoolean(key: String?, defValue: Boolean): Boolean {
        return preferences.getBoolean(key, defValue)
    }

    private class Builder internal constructor(context: Context?) {
        private val context: Context

        /**
         * Method that creates an instance of Prefs
         *
         * @return an instance of Prefs
         */
        fun build(): Prefs {
            return Prefs(context)
        }

        init {
            requireNotNull(context) { "Context must not be null." }
            this.context = context.applicationContext
        }
    }

    companion object {
        private const val TAG = "Prefs"
        private var singleton: Prefs? = null
        private lateinit var preferences: SharedPreferences
        private lateinit var editor: SharedPreferences.Editor

        @JvmStatic
        fun with(context: Context?): Prefs? {
            if (singleton == null) {
                singleton = Builder(context).build()
            }
            return singleton
        }
    }

    init {
        preferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE)
        editor = preferences.edit()
    }
}