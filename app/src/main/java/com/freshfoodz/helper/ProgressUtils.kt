package com.freshfoodz.helper

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.freshfoodz.custom.CustomProgressBar

object ProgressUtils {

    private lateinit var customProgressBar: CustomProgressBar

    fun showProgress(context: Context?, title: String) {
        customProgressBar = CustomProgressBar.with(title)
        customProgressBar.show(
            (context as AppCompatActivity).supportFragmentManager,
            CustomProgressBar.TAG
        )
    }

    fun showProgress(context: Context?) {
        customProgressBar = CustomProgressBar.with("Loading..")
        customProgressBar.show(
            (context as AppCompatActivity).supportFragmentManager,
            CustomProgressBar.TAG
        )
    }

    fun changeText(text: String) {
        if (isProgressVisible()) {
            customProgressBar.changeText(text)
        }
    }

    fun hideProgress() {
        try {
            if (isProgressVisible()) {
                customProgressBar.dismissAllowingStateLoss()
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    private fun isProgressVisible(): Boolean {
        return customProgressBar.isVisible && customProgressBar.isAdded
    }
}