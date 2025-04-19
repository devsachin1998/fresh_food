package com.freshfoodz.helper

import android.graphics.Typeface
import android.text.SpannableString
import android.text.style.*
import android.view.View

class AdvancedSpannableString(source: CharSequence) : SpannableString(source) {
    private val mainString: String = source.toString()
    private var listener: OnClickableSpanListener? = null

    fun setSpanClickListener(listener: OnClickableSpanListener?) {
        this.listener = listener
    }

    fun setColor(color: Int, subString: String) {
        val start = mainString.indexOf(subString)
        val end = start + subString.length
        val span = ForegroundColorSpan(color)
        setSpan(span, start, end, SPAN_EXCLUSIVE_EXCLUSIVE)
    }

    fun setBold(subString: String) {
        val start = mainString.indexOf(subString)
        val end = start + subString.length
        val span = StyleSpan(Typeface.BOLD)
        setSpan(span, start, end, SPAN_EXCLUSIVE_EXCLUSIVE)
    }

    fun setItalic(subString: String) {
        val start = mainString.indexOf(subString)
        val end = start + subString.length
        val span = StyleSpan(Typeface.ITALIC)
        setSpan(span, start, end, SPAN_EXCLUSIVE_EXCLUSIVE)
    }

    fun setBoldItalic(subString: String) {
        val start = mainString.indexOf(subString)
        val end = start + subString.length
        val span = StyleSpan(Typeface.BOLD_ITALIC)
        setSpan(span, start, end, SPAN_EXCLUSIVE_EXCLUSIVE)
    }

    fun setCustomSizeOf(size: Float, subString: String) {
        val start = mainString.indexOf(subString)
        val end = start + subString.length
        val span = RelativeSizeSpan(size)
        setSpan(span, start, end, SPAN_EXCLUSIVE_EXCLUSIVE)
    }

    fun setStrikeThroughIn(subString: String) {
        val start = mainString.indexOf(subString)
        val end = start + subString.length
        val span = StrikethroughSpan()
        setSpan(span, start, end, SPAN_EXCLUSIVE_EXCLUSIVE)
    }

    fun setUnderLine(subString: String) {
        val start = mainString.indexOf(subString)
        val end = start + subString.length
        val span = UnderlineSpan()
        setSpan(span, start, end, SPAN_EXCLUSIVE_EXCLUSIVE)
    }

    fun setClickableSpanTo(subString: String) {
        val start = mainString.indexOf(subString)
        val end = start + subString.length
        val span: ClickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                listener!!.onSpanClick()
            }
        }
        setSpan(span, start, end, SPAN_EXCLUSIVE_EXCLUSIVE)
    }

    fun setUrlSpan(url: String?, subString: String) {
        val start = mainString.indexOf(subString)
        val end = start + subString.length
        val span = URLSpan(url)
        setSpan(span, start, end, SPAN_EXCLUSIVE_EXCLUSIVE)
    }

    interface OnClickableSpanListener {
        fun onSpanClick()
    }

}