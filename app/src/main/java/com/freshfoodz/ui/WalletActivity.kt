package com.freshfoodz.ui

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.freshfoodz.R
import com.freshfoodz.adapter.WalletAdapter
import com.freshfoodz.databinding.ActivityWalletBinding
import com.freshfoodz.dialog.AddMoneyDialog
import com.freshfoodz.helper.closeScreen
import com.freshfoodz.helper.toast
import com.freshfoodz.model.WalletHistoryResponse
import com.freshfoodz.viewmodel.factory.WalletViewModelFactory
import com.freshfoodz.viewmodel.vm.WalletViewModel

class WalletActivity : AppCompatActivity(), WalletViewModel.WalletCallBack, DialogInterface.OnDismissListener {

    private lateinit var binding: ActivityWalletBinding
    private lateinit var viewModel: WalletViewModel
    private lateinit var adapter: WalletAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_wallet)
        init()
    }

    private fun init() {
        setSupportActionBar(binding.toolbarLayout.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
        binding.toolbarLayout.toolbar.title = ""

        adapter = WalletAdapter()
        binding.rvPaymentHistory.adapter = adapter

        viewModel = ViewModelProvider(
            this,
            WalletViewModelFactory(this, this)
        ).get(WalletViewModel::class.java)
        viewModel.getWalletHistory()

        initClick()
    }

    private fun initClick() {
        binding.toolbarLayout.toolbar.setNavigationOnClickListener { closeScreen() }
        binding.fabAdd.setOnClickListener {
            val dialog = AddMoneyDialog.newInstance()
            dialog.show(supportFragmentManager, AddMoneyDialog.TAG)
            /*dialog.onDismiss(object : DialogInterface {
                override fun cancel() {

                }

                override fun dismiss() {
                    viewModel.getWalletHistory()
                }
            })*/
        }
    }

    override fun onBackPressed() {
        closeScreen()
    }

    override fun onEmpty() {
        binding.toolbarLayout.txtBalance.text = "0 ₹"
        binding.emptyLayout.txtEmpty.visibility = View.VISIBLE
        binding.emptyLayout.txtEmpty.text = "No transactions"
    }

    override fun onWallet(obj: WalletHistoryResponse.Wallet) {
        binding.toolbarLayout.txtBalance.text = "${obj.newBalance} ₹"
        adapter.setItems(obj._historyList)
    }

    override fun onError(error: String) {
        toast(error)
    }

    override fun onDismiss(p0: DialogInterface?) {
        viewModel.getWalletHistory()
    }
}