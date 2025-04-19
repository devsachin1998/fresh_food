package com.freshfoodz.custom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.freshfoodz.R
import com.freshfoodz.databinding.ProgressHudBinding
import com.freshfoodz.helper.AppConstants

class CustomProgressBar : DialogFragment() {

    private lateinit var binding: ProgressHudBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.progress_hud, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()
    }

    private fun setUpView() {
        binding.message.text = arguments?.getString(AppConstants.ALERT_TITLE)
    }

    override fun onStart() {
        super.onStart()
        dialog?.setCancelable(false)
        dialog?.setCanceledOnTouchOutside(false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.ProgressHUD)
    }

    companion object {

        const val TAG = "CustomProgressBar"

        fun with(title: String?): CustomProgressBar {
            val args = Bundle()
            args.putString(AppConstants.ALERT_TITLE, title)
            val fragment = CustomProgressBar()
            fragment.arguments = args
            return fragment
        }
    }

    fun changeText(text: String) {
        binding.message.text = text
    }
}