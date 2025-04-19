package com.freshfoodz.helper

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.text.*
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatAutoCompleteTextView
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.freshfoodz.R
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

private const val EMAIL_PATTERN = ("^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@"
        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")

// to check email id is valid or  not
fun emailNotValid(email: String?): Boolean {
    val pattern = Pattern.compile(EMAIL_PATTERN)
    val matcher = pattern.matcher(email)
    return !matcher.matches()
}

fun Context.isConnected(): Boolean {
    val connectivityManager =
        (getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
    val activeNetworkInfo = connectivityManager.activeNetworkInfo
    return activeNetworkInfo != null && activeNetworkInfo.isConnected
}

// to show toast
fun Context.toast(message: CharSequence) {
    if (TextUtils.isEmpty(message)) {
        return
    }
    val view = View.inflate(this, R.layout.layout_toast, null)
    val textView = view.findViewById(R.id.txtTopError) as TextView
    textView.text = message
    val toast = Toast(this)
    toast.setGravity(Gravity.BOTTOM or Gravity.FILL_HORIZONTAL, 0, 0)
    toast.duration = Toast.LENGTH_LONG
    toast.view = view
    toast.show()
}

// intent function from Activity, clearAll will clean the stack
fun Activity.fireIntent(cls: Class<*>, clearAll: Boolean = false) {
    val i = Intent(this, cls)
    if (clearAll) {
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
    } else {
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    startActivity(i)
    this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
}

// intent function from Context, clearAll will clean the stack
fun Context.fireIntent(cls: Class<*>, clearAll: Boolean = false) {
    val i = Intent(this, cls)
    if (clearAll) {
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
    } else {
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    startActivity(i)
    (this as Activity).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
}

fun Context.fireIntent(cls: Class<*>, clearAll: Boolean, bundle: Bundle, key: String) {
    val i = Intent(this, cls)
    if (clearAll) {
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
    } else {
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    i.putExtra(key, bundle)
    startActivity(i)
    (this as Activity).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
}

// finish the screen
fun Activity.closeScreen() {
    this.finish()
    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
}

// finish the screen using context
fun Context.closeScreen() {
    (this as AppCompatActivity).finish()
    this.overridePendingTransition(
        android.R.anim.fade_in,
        android.R.anim.fade_out
    )
}

// get string from EditText
fun EditText.toStr(): String {
    return this.text.toString().trim()
}

// close keyboard
fun Context.closeKeyPad() {
    try {
        val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        var view: View? = (this as Activity).currentFocus
        if (view == null) {
            view = View(this)
        }
        inputManager.hideSoftInputFromWindow(
            view.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

// Close keyboard when user clicks outside the keyboard
@SuppressLint("ClickableViewAccessibility")
fun setupUI(var0: Context?, var1: View) {
    if (var1 !is EditText) {
        var1.setOnTouchListener { var1, var2 ->
            (var0 as Activity).closeKeyPad()
            false
        }
    }
    if (var1 is ViewGroup) {
        for (var2 in 0 until var1.childCount) {
            setupUI(var0, var1.getChildAt(var2))
        }
    }
}

// for text-watcher for EditText
fun EditText.afterTextChanged(callback: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(
            s: CharSequence?,
            start: Int,
            count: Int,
            after: Int
        ) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(editable: Editable?) {
            callback.invoke(editable.toString())
        }
    })
}

// for text-watcher for EditText
fun EditText.onTextChanged(callback: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(
            s: CharSequence?,
            start: Int,
            count: Int,
            after: Int
        ) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            callback.invoke(s.toString())
        }

        override fun afterTextChanged(editable: Editable?) {
        }
    })
}

// for EditText done button called
fun EditText.onDone(callback: () -> Unit) {
    imeOptions = EditorInfo.IME_ACTION_DONE // android:imeOptions="actionDone"
    maxLines = 1
    setOnEditorActionListener { _, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            callback.invoke()
            true
        }
        false
    }
}

// for append spans
fun SpannableStringBuilder.spansAppend(
    text: CharSequence,
    flags: Int,
    vararg spans: Any
): SpannableStringBuilder {
    val start = length
    append(text)

    spans.forEach { span ->
        setSpan(span, start, length, flags)
    }

    return this
}

fun AppCompatAutoCompleteTextView.afterTextChanged(callback: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(
            s: CharSequence?,
            start: Int,
            count: Int,
            after: Int
        ) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(editable: Editable?) {
            callback.invoke(editable.toString())
        }
    })
}

// countdown timer for specific millis
fun countDownTimer(millis: Long, callback: () -> Unit) {
    object : CountDownTimer(millis, 1000) {
        override fun onTick(p0: Long) {

        }

        override fun onFinish() {
            callback.invoke()
        }
    }.start()
}

// show snack
fun Context.showSnack(message: String?) {
    if (TextUtils.isEmpty(message)) {
        return
    }
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

// run-time permission
fun Context.askPermission() {

    TedPermission.create()
        .setPermissions(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.SET_WALLPAPER,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        .setPermissionListener(object : PermissionListener {
            override fun onPermissionGranted() {}
            override fun onPermissionDenied(deniedPermissions: List<String>) {
                showSnack(getString(R.string.required_permission))
            }
        })
        .setDeniedCloseButtonText(getString(R.string.reject_permission))
        .check()
}

// convert Dp To Pixel
fun Context.convertDpToPixel(dp: Float): Float {
    return dp * (resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}

// open web url using intent
fun Context.openIntentUrl(url: String) {
    try {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse(url)
            ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        )
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

// get random integer value
fun getRandomInt(min: Int, max: Int): Int {
    return Random().nextInt(max - min + 1) + min
}

// edittext blank or not
fun EditText.isNullEmpty(): Boolean {
    return text.toString().trim().isEmpty()
}

// edittext value
fun EditText.value(): String {
    return text.toString().trim()
}

// changing date-time formatting
fun formatDate(date: String?, inputFormat: String?, outputFormat: String?): String {
    var result = ""
    var inputSDF: SimpleDateFormat?
    var outputSDF: SimpleDateFormat?
    try {
        inputSDF = SimpleDateFormat(inputFormat, Locale.US)
        outputSDF = SimpleDateFormat(outputFormat, Locale.US)
        result = outputSDF.format(inputSDF.parse(date))
    } catch (e: Exception) {
        e.printStackTrace()
        return ""
    } finally {
        inputSDF = null
        outputSDF = null
    }
    return result
}


fun Context.showAlert(
    title: String?,
    message: String?,
    btnPositive: String?,
    btnNegative: String?,
    cancel:Boolean?,
    listener: OnOptionListener
) {
    val builder = AlertDialog.Builder(this)
    builder.setTitle(title)
    if (!TextUtils.isEmpty(message)) {
        builder.setMessage(message)
    }
    if (!TextUtils.isEmpty(btnPositive)) {
        builder.setPositiveButton(btnPositive) { dialog: DialogInterface, id: Int ->
            dialog.dismiss()
            listener.onClick(true)
        }
    }
    if (!TextUtils.isEmpty(btnNegative)) {
        builder.setNegativeButton(btnNegative) { dialog: DialogInterface, id: Int ->
            // User cancelled the dialog
            dialog.dismiss()
            listener.onClick(false)
        }
    }
    val dialog = builder.create()
    if(cancel == true)
    {
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
    }
    else

    {
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
    }

    dialog.show()

}

fun Context.callIntent(number: String) {
    val callIntent = Intent(Intent.ACTION_DIAL)
    callIntent.data = Uri.parse("tel:$number")
    startActivity(callIntent)
}

fun EditText.onlyNumbers() {
    setRawInputType(InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL)
    /*inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL or
            InputType.TYPE_NUMBER_FLAG_SIGNED
    keyListener = DigitsKeyListener.getInstance("0123456789")*/
}

interface OnOptionListener {
    fun onClick(isYes: Boolean)
}