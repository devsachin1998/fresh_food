package com.freshfoodz.dialog

import android.content.DialogInterface
import android.os.Bundle
import android.text.InputFilter
import android.text.TextUtils
import android.view.*
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.freshfoodz.R
import com.freshfoodz.api.ApiClient
import com.freshfoodz.api.ApiConstants
import com.freshfoodz.databinding.DialogAddMoneyBinding
import com.freshfoodz.helper.*
import com.freshfoodz.model.AddMoneyRequest
import com.freshfoodz.model.LoginRes


class AddMoneyDialog : DialogFragment() {

    companion object {

        const val TAG: String = "AddMoneyDialog"

        fun newInstance(): AddMoneyDialog {
            val args = Bundle()
            val fragment = AddMoneyDialog()
            fragment.arguments = args
            return fragment
        }

    }

    private lateinit var profile: LoginRes.UserProfile
    private lateinit var binding: DialogAddMoneyBinding
    private var current: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_add_money, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()
    }

    private fun setUpView() {
        binding.toolbarLayout.toolbar.title = "Add Money"
        binding.toolbarLayout.imgProfile.setImageResource(R.drawable.ic_close)
        binding.toolbarLayout.imgcall.visibility=View.GONE
        binding.toolbarLayout.imgProfile.visibility=View.VISIBLE

        val params = Toolbar.LayoutParams(
            Toolbar.LayoutParams.WRAP_CONTENT,
            Toolbar.LayoutParams.WRAP_CONTENT
        )
        params.setMargins(8, 8, 8, 8)
        params.gravity = Gravity.END
        binding.toolbarLayout.imgProfile.layoutParams = params

        binding.toolbarLayout.imgProfile.setOnClickListener { dismissAllowingStateLoss() }
        binding.btnAdd.setOnClickListener {
            addAmount()
        }
        binding.edtAmount.onDone { addAmount() }

        profile = PrefUtils.getProfile(requireContext())

        val filter =
            InputFilter { source, start, end, dest, dstart, dend ->
                for (i in start until end) {
                    if (!Character.isDigit(source[i])) {
                        return@InputFilter ""
                    }
                }
                null
            }
        binding.edtAmount.filters = arrayOf(filter)
    }

    private fun addAmount() {
        if (TextUtils.isEmpty(binding.edtAmount.value())) {
            requireActivity().toast("Amount is required")
            return
        }
        val req =
            AddMoneyRequest(binding.edtAmount.value().toInt(), profile.UserID, profile.WalletID)
        ApiClient(requireContext()).addMoney(req).observe(requireActivity(), {
            if (it.response_code == ApiConstants.SUCCESS.toString()) {
                requireActivity().showAlert(it.message, "", "OK", "",true, object : OnOptionListener {
                    override fun onClick(isYes: Boolean) {
                        if (isYes) {
                            dismissAllowingStateLoss()
                        }
                    }
                })
            } else {
                requireActivity().toast(it.message)
            }
        })
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog?.setCancelable(false)
        dialog?.setCanceledOnTouchOutside(false)
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        if (requireActivity() is DialogInterface.OnDismissListener) {
            (requireActivity() as DialogInterface.OnDismissListener).onDismiss(dialog)
        }
    }
}