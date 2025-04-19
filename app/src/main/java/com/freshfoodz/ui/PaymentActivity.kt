package com.freshfoodz.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.freshfoodz.R
import com.freshfoodz.api.ApiClient
import com.freshfoodz.api.ApiConstants
import com.freshfoodz.databinding.ActivityPaymentBinding
import com.freshfoodz.helper.*
import com.freshfoodz.model.AddPaymentRequest
import com.freshfoodz.model.LoginRes
import com.gun0912.tedpermission.provider.TedPermissionProvider.context
import com.razorpay.*
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class PaymentActivity : AppCompatActivity(), PaymentResultWithDataListener, ExternalWalletListener {

    // on below line we are creating
    // variables for our edit text and button
    lateinit var amtEdt: EditText
    lateinit var payBtn: Button
    private lateinit var binding: ActivityPaymentBinding
    private var bal =0.0
    private var amt="";
    private lateinit var profile: LoginRes.UserProfile

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_payment)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_payment)
        bal = (intent.extras?.getDouble("bal") ?: 0.0)

        setSupportActionBar(binding.toolbarLayout.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbarLayout.toolbar.title = "Payment"
        binding.toolbarLayout.imgProfile.visibility = View.GONE
        binding.toolbarLayout.imgcall.visibility = View.GONE
        binding.toolbarLayout.llamountbal.visibility = View.GONE
        binding.toolbarLayout.toolbar.setNavigationOnClickListener { closeScreen() }
        if(bal<0)
        {

            binding.balamounttxt.text="Your Outstanding amount is";
            binding.balamount.text="₹ "+bal;

            binding.balamount.setTextColor(Color.parseColor("#FF0000"))

            var balance=bal.toString();
            binding.amount.setText(""+balance.replace("-",""));
         }
        else
        {
            binding.balamounttxt.text="Your Balance amount is Rs. ";
            binding.balamount.text="₹ "+bal;
            binding.balamount.setTextColor(Color.parseColor("#008000"))


        }


       // getwallet();
        // on below line we are initializing
        // our variable with their ids.
//        amtEdt = findViewById(R.id.idEdtAmt)
//        payBtn = findViewById(R.id.idBtnMakePaymanet)
//
//        // on below line adding click listener for pay button
        binding.paybutton1.setOnClickListener {
           var am="500";
            binding.amount.setText(am);
        }
        binding.paybutton2.setOnClickListener {
            var am="1000";
            binding.amount.setText(am);
        }
        binding.paybutton3.setOnClickListener {
            var am="1500";
            binding.amount.setText(am);
        }
        binding.trnsaction.setOnClickListener {
            fireIntent(WalletActivity::class.java)

        }
        binding.Continue.setOnClickListener {
            if (binding.amount.text.toString() =="0" || binding.amount.text.toString() =="") {
                toast("Please Enter Amount");

            } else {

                // on below line getting amount from edit text
                 amt = binding.amount.text.toString()

                // rounding off the amount.
                val amount = Math.round(amt.toFloat() * 100).toInt()

                // on below line we are
                // initializing razorpay account
                val checkout = Checkout()

                // on the below line we have to see our id.
               //  checkout.setKeyID("rzp_test_ZMw0fZPfUkOsSe")
                checkout.setKeyID("rzp_live_G9JbpR9cnycAYw")

                // set image
                checkout.setImage(R.mipmap.ic_launcher)

                // initialize json object
                val obj = JSONObject()
                try {
                    // to put name
                    obj.put("name", "Fresh Foodz")

                    // put description
//                obj.put("description", "Test payment")

                    // to set theme color
                    obj.put("theme.color", "")

                    // put the currency
                    obj.put("currency", "INR")

                    // put amount
                    obj.put("amount", amount)

                    // put mobile number
                    profile = PrefUtils.getProfile(this)
                    obj.put("prefill.contact", profile.MobileNo)

                    /// put email
                      obj.put("prefill.email", profile.Email)

                    // open razorpay to checkout activity
                    checkout.open(this@PaymentActivity, obj)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
        }
    }


//    override fun onPaymentSuccess(s: String?, PaymentData paymentData) {
//        // this method is called on payment success.
//      //  Toast.makeText(this, "Payment is successful : " + s, Toast.LENGTH_SHORT).show();
//        fireIntent(PaymentStatus::class.java)
//        val intent = Intent(this, PaymentStatus::class.java);
//        intent.putExtra("paymentstatus", "1");
//        startActivity(intent)
//
//    }
//
//    override fun onPaymentError(p0: Int, s: String?,PaymentData paymentData) {
//        // on payment failed.
////        fireIntent(
////            SignupDoneActivity::class.java,
////            true
////        )
//        val intent = Intent(this, PaymentStatus::class.java);
//        intent.putExtra("paymentstatus", "0");
//        startActivity(intent)
//
//
//        Toast.makeText(this, "Payment Failed due to error : " + s, Toast.LENGTH_SHORT).show();
//    }


    override fun onBackPressed() {
        closeScreen()
    }

    override fun onPaymentSuccess(p0: String?, p1: PaymentData?) {


        val intent = Intent(this, PaymentStatus::class.java);
        intent.putExtra("paymentstatus", "1");
        intent.putExtra("amt", amt);
        startActivity(intent)
    }

    override fun onPaymentError(p0: Int, p1: String?, p2: PaymentData?) {

        val intent = Intent(this, PaymentStatus::class.java);
        intent.putExtra("paymentstatus", "0");
        intent.putExtra("amt", amt);
        startActivity(intent)
//        val intent = Intent(this, PaymentStatus::class.java);
//        intent.putExtra("paymentstatus", "0");
//        startActivity(intent)

    }

    override fun onExternalWalletSelected(p0: String?, p1: PaymentData?) {
//        val intent = Intent(this, PaymentStatus::class.java);
//        intent.putExtra("paymentstatus", "0");
//        startActivity(intent)
    }
}