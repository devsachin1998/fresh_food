package com.freshfoodz.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.freshfoodz.R
import com.freshfoodz.api.ApiClient
import com.freshfoodz.api.ApiConstants
import com.freshfoodz.databinding.ActivityPaymentBinding
import com.freshfoodz.databinding.ActivityPaymentStatusBinding
import com.freshfoodz.helper.PrefUtils
import com.freshfoodz.helper.closeScreen
import com.freshfoodz.helper.fireIntent
import com.freshfoodz.model.AddPaymentRequest
import com.freshfoodz.model.LoginRes
import com.gun0912.tedpermission.provider.TedPermissionProvider.context
import java.util.*

class PaymentStatus : AppCompatActivity() {
    var paymentstatus ="0";
    var amount ="0";
    private lateinit var binding: ActivityPaymentStatusBinding
    private lateinit var profile: LoginRes.UserProfile

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_payment_status)

        paymentstatus = (intent.extras?.getString("paymentstatus") ?: "1")
        amount = (intent.extras?.getString("amt") ?: "0")

        updatepayment(paymentstatus)

        binding.btnhome.setOnClickListener {
            fireIntent(DashboardActivity::class.java, true)

        }


    }

    override fun onBackPressed() {
        fireIntent(DashboardActivity::class.java, true)
    }

    private fun updatepayment(value: String)
    {
        profile = PrefUtils.getProfile(this)
        var status=""
        if(value=="1")
        {
            status="Success"
        }
        else
        {
            status="Fail"
        }

        val rnd = Random()
        val randnoint = rnd.nextInt(999999)
        val req =
            AddPaymentRequest(randnoint, profile.UserID,profile.Name ,amount.toDouble(),"Online",status)
        ApiClient(this@PaymentStatus).addPaymentdetail(req).observe(this@PaymentStatus, {
            if (it.response_code == ApiConstants.SUCCESS.toString()) {
binding.ll1.visibility=View.VISIBLE;
                if(paymentstatus=="1" && it.obj.PaymentStatus=="Success")

                {
                    binding.imgs.visibility=View.VISIBLE;
                    binding.txtstatus.visibility=View.VISIBLE;

                    binding.txtstatus.text="Transaction Successful !"
//            binding.txtstatus.setTextColor(R.color.green);
                    binding.txtmsg.text="You transaction has been Successfully Done.Your updated balance amount is "+it.obj.Balance.toString()+".\n Thanks for using Fresh Foodz"
                }
                else
                {
                    binding.imgc.visibility=View.VISIBLE;
                    binding.txtstatus1.text="Transaction Failed !"
                    binding.txtstatus1.visibility=View.VISIBLE;

                    binding.txtmsg.text="You transaction was not Successful due some reason. Please try again afrer 5 minnutes. Please contact us if you are not able to make online payment. \n Thanks for using Fresh Foodz"
                }
//
//                val intent = Intent(this, PaymentStatus::class.java);
//                intent.putExtra("paymentstatus", value);
//                startActivity(intent)
            } else {
                //  requireActivity().toast(it.message)
            }
        })
    }
}