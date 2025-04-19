package com.freshfoodz.ui

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.freshfoodz.R
import com.freshfoodz.adapter.CategoryAdapter
import com.freshfoodz.adapter.StockListAdapter
import com.freshfoodz.api.ApiClient
import com.freshfoodz.api.ApiConstants
import com.freshfoodz.databinding.ActivityDashboardBinding
import com.freshfoodz.helper.*
import com.freshfoodz.helper.PrefUtils.saveStringValue
import com.freshfoodz.model.LoginRes
import com.freshfoodz.model.StartUpResponse
import com.freshfoodz.model.StockListResponse
import com.freshfoodz.model.SubItem
import com.freshfoodz.room.FoodDBHelper
import com.google.android.flexbox.*
import com.google.android.material.navigation.NavigationView
import com.gun0912.tedpermission.provider.TedPermissionProvider.context


class DashboardActivity : AppCompatActivity(), StockListAdapter.OnUpdateListener,
    CategoryAdapter.OnCategorySelectionListener, NavigationView.OnNavigationItemSelectedListener  {

    private lateinit var binding: ActivityDashboardBinding
    private lateinit var adapter: StockListAdapter
    private lateinit var catAdapter: CategoryAdapter
    private lateinit var startUp: StartUpResponse.StartUp
    private lateinit var getitem: Array<StockListResponse.Item>
    private var items = arrayListOf<StockListResponse.Item>()
    private var allItems = arrayListOf<StockListResponse.Item>()
    private var selectedIds = arrayListOf<Int>()
    private var bal =0.0
    private var IsNotification="0"
    private lateinit var txtname: TextView
    private lateinit var txtmobbile: TextView
    private lateinit var textViewcell: TextView

    private lateinit var profile: LoginRes.UserProfile
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard)
        IsNotification = (intent.extras?.getString("IsNotification") ?: "0")

        init()


        setSupportActionBar(binding.toolbarLayout.toolbar)
        val toggle = ActionBarDrawerToggle(
            this, binding.drawerLayout, binding.toolbarLayout.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        val header: View = binding.navView.getHeaderView(0)

        binding.navView.setNavigationItemSelectedListener(this)
      //  txtname = (header.findViewById<View>(R.id.txtname) as TextView).toString()
        txtname = header.findViewById(R.id.txtname);
        txtmobbile = header.findViewById(R.id.txtmob);
        textViewcell = header.findViewById(R.id.textViewcell);

        displayScreen(-1)
        adapter = StockListAdapter(this)

        startUp = PrefUtils.getStartUp(this)

        val version = startUp.Version.toString();
        if(!version.equals(GetAppVersion(this)))
        {
           Update()
        }

        if (startUp._categoryList.isNotEmpty()) {
            catAdapter = CategoryAdapter(startUp._categoryList, this)
            binding.rvCategories.visibility = View.VISIBLE
//            binding.rvCategories.setLayoutManager(
//                LinearLayoutManager(
//                    this,
//                    LinearLayoutManager.HORIZONTAL,
//                    true
//                )
//            )
//
//            val layoutManager = FlexboxLayoutManager(this)
//            layoutManager.flexDirection = FlexDirection.ROW
//            layoutManager.flexWrap = FlexWrap.WRAP
//            layoutManager.justifyContent = JustifyContent.FLEX_START
//            layoutManager.alignItems = AlignItems.FLEX_START
//            binding.rvCategories.layoutManager = layoutManager

            binding.rvCategories.adapter = catAdapter
        }
        getwallet();
        getitem();
      // getlocal();



    }
    private fun setUserDetails() {
        profile = PrefUtils.getProfile(this)

        txtname.text = profile.Name.toString()
        txtmobbile.text = profile.MobileNo
        textViewcell.text = profile.Name.substring(0, 1).uppercase();

        //binding.txtEmail.text = profile.Email
    }

    private fun getlocal()
    {
        binding.edtSearch.setText("")
        items = arrayListOf()
        allItems = arrayListOf()
        getitem= PrefUtils.getitem(this)!!


                binding.emptyLayout.txtEmpty.visibility = View.GONE
                allItems.addAll(getitem)
                items.addAll(getitem)
             //   PrefUtils.saveObjectValue(this, PrefUtils.item_all, items)

                adapter.setItems(items)
                if (selectedIds.isNotEmpty()) {
                    items = arrayListOf()
                    for (item in allItems) {
                        if (selectedIds.contains(item.CategoryID)) {
                            items.add(item)
                        }
                    }
                    adapter.setItems(items)
                }
                refreshCart()
//            } else {
//                binding.emptyLayout.txtEmpty.visibility = View.VISIBLE
//                binding.emptyLayout.txtEmpty.text = "No products"
//            }
       // }
    }

    override fun onStart() {
        super.onStart()


    }
    fun displayScreen(id: Int){

        // val fragment =  when (id){

        when (id){
            R.id.nav_order -> {
                fireIntent(MyOrdersActivity::class.java)
            }
            R.id.nav_transaction -> {
                fireIntent(WalletActivity::class.java)
            }
            R.id.action_profile -> {
                fireIntent(ProfileActivity::class.java)
            }
            R.id.nav_payment -> {

                val intent = Intent(this, PaymentActivity::class.java);
                intent.putExtra("bal", bal);
                startActivity(intent)
            }
            R.id.nav_share -> {
                shareWhatsApp();
            }
            R.id.nav_password -> {
                fireIntent(ChangePasswordActivity::class.java)
            }
            R.id.nav_logout -> {
                showAlert();
            }



        }
    }

    /* private class Settext extends AsyncTask<String, Void, String> {
        boolean userfound;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            String responseString = null;
            try {
                if (common.is_internet_connected()) {
                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                    nameValuePairs.add(new BasicNameValuePair("CustId", common.getSession(ConstValue.COMMON_KEY)));
                    JSONParser jsonParser = new JSONParser();

                    String json_responce = jsonParser.makeHttpRequest(ConstValue.SETTEXT, "POST", nameValuePairs);
                    json_responce = json_responce.replace("\n", "");
                    JSONObject jObj = new JSONObject(json_responce);
                    if (jObj.has("Message") && !jObj.getString("Message").equalsIgnoreCase("false")) {
                        comment = jObj.getString("Message");
                        userfound = true;
                    } else {
                        userfound = false;
                    }
                } else {
                    responseString = ConstValue.COMMON_INTERNETMSG;
                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                responseString = e.getMessage();
                CommonClass.writelog(tag, "Settext:doInBackground() 436 :JSONException Error: " + e.getMessage());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                responseString = e.getMessage();
                e.printStackTrace();
                CommonClass.writelog(tag, "Settext:doInBackground() 441 :IOException Error: " + e.getMessage());
            }
            // TODO: register the new account here.

            return responseString;
        }

        @Override
        protected void onPostExecute(final String success) {
            try {
                if (success != null) {
                    if (success.equals(ConstValue.COMMON_INTERNETMSG)) {
                        CommonClass.AppCrashScreen(getApplicationContext(), 1, tag,success);
                    } else {
                        CommonClass.AppCrashScreen(getApplicationContext(), 0, tag,success);
                    }
                } else {
                    if (userfound) {
                        textcomment.setText(comment);
                    } else {
                        common.setToastMessage("Error in Getting Text.");
                    }
                }
                resumeHasRun = false;
            }
            catch (Exception ex)
            {
                CommonClass.writelog(tag, "Settext:onPostExecute() 471 :Exception Error: " + ex.getMessage());
                CommonClass.AppCrashScreen(getApplicationContext(), 0, tag,ex.getMessage());
            }
        }
    }*/
    fun shareWhatsApp() {
        try {
            profile = PrefUtils.getProfile(this)

            var Message = ""
            var Name = profile.Name;

                Message =
                    "has invited you to download Fresh Foodz app to get fresh vegetables delivered at your doorstep." +
                            " Download:  http://bitly.ws/uTQS."



            if (isWhatssAppInstalled()) {
                val waIntent = Intent(Intent.ACTION_SEND)
                waIntent.type = "text/plain"

//                val uri =
//                    Uri.parse("http://admin.freshfoodz.in.net/Content/ff.png")

                /*   String text = "Hello!l like this product on Sosho.in and thought of sharing with you!" +
                                " They’ve got some amazing group buy discounts," +
                                " valid only till 07 Nov 2019 12:00PM. How about we buy it together?";*/

                val text =
                    "$Name $Message"
                waIntent.setPackage("com.whatsapp")
                waIntent.putExtra(Intent.EXTRA_TEXT, text)
//                waIntent.putExtra(Intent.EXTRA_STREAM,uri);
//                waIntent.setType("image/*");
//                val uri =
//                    Uri.parse("android.resource://" + context.packageName + "/drawable/temp_image")
////
////                val uri = Uri.parse("http://admin.freshfoodz.in.net/Content/ff.png")
//
//                waIntent.putExtra(Intent.EXTRA_STREAM, uri);
//                waIntent.setType("image/*");
                waIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);


                startActivity(Intent.createChooser(waIntent, "Share with"))
            } else {
                DownloadWhatsapp(this)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    fun DownloadWhatsapp(context: Context?) {
        val alertDialog = AlertDialog.Builder(context)
        alertDialog.setTitle("Warning")
        alertDialog.setMessage("WhatsApp is not installed.Please install from Playstore")
        alertDialog.setNegativeButton(
            "NO"
        ) { dialog, which -> }
        alertDialog.setPositiveButton(
            "YES"
        ) { dialog, which ->
            try {
                val browserIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=com.whatsapp")
                )
                browserIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                        Intent.FLAG_ACTIVITY_CLEAR_TOP or
                        Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(browserIntent)
            } catch (ex: java.lang.Exception) {
                ex.printStackTrace()
                val browserIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=com.whatsapp")
                )
                browserIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                        Intent.FLAG_ACTIVITY_CLEAR_TOP or
                        Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(browserIntent)
            }
        }
        alertDialog.show()
    }
    fun isWhatssAppInstalled(): Boolean {
        val pm: PackageManager = getPackageManager()
        return try {
            pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

    private fun init() {
        initClick()
        refreshCart()
       val s =PrefUtils.getStringValue(this, "IS_NOTIFICATION")
        if(IsNotification=="1") {
            if (PrefUtils.getStringValue(this, "IS_NOTIFICATION").equals("1")) {
                saveStringValue(this, "IS_NOTIFICATION", "")
                saveStringValue(this, "NOTIFICATION_COUNT", "")
                fireIntent(NotificationActivity::class.java)

            }
        }
        else
        {
            val s =PrefUtils.getStringValue(this, "NOTIFICATION_COUNT")
            if(s.equals("") || s.equals("0"))
            {
                binding.toolbarLayout.txtNotificationCount.visibility = View.GONE

            }
            else {
                binding.toolbarLayout.txtNotificationCount.visibility = View.VISIBLE
                binding.toolbarLayout.txtNotificationCount.text = s
            }

        }
      //  addAutoStartup();

    }
    private fun showAlert() {
        showAlert("Logout", "Are you sure you want to logout?", "Yes", "No",true,
            object : OnOptionListener {
                override fun onClick(isYes: Boolean) {
                    if (isYes) {
                        clearSession()
                    }
                }
            })
    }
    private fun Update() {
        showAlert("Update information", getResources().getString(R.string.newversiontxt), "Update", "",false,
            object : OnOptionListener {
                override fun onClick(isYes: Boolean) {
                    if (isYes) {

//                                Intent browserIntent = new Intent(
//                                        Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.taazafood"));
//                                browserIntent.addCategory(Intent.CATEGORY_HOME);
//                                browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
//                                        | Intent.FLAG_ACTIVITY_CLEAR_TOP
//                                        | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                                startActivity(browserIntent);
//                                common.onClickAnimation(home_activity_new.this);
                        // Change By Tejas Patel 25 March 2019
                        try {
                            startActivity(
                                Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse("market://details?id=com.freshfoodz")
                                )
                            )
                        } catch (anfe: ActivityNotFoundException) {
                            startActivity(
                                Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse("https://play.google.com/store/apps/details?id=com.freshfoodz")
                                )
                            )
                        }
                        finish()
                    }
                }
            })

    }
    private fun clearSession() {
        PrefUtils.saveBoolean(this, PrefUtils.IS_LOGGED_IN, false)
        FoodDBHelper.clearCart(this)
        saveStringValue(this, "fcmtoken", "")

        fireIntent(SplashActivity::class.java)
    }
    private fun getitem()
    {
        binding.edtSearch.setText("")
        items = arrayListOf()
       allItems = arrayListOf()
        ApiClient(this).getStockList().observe(this, {
            if (it.response_code == ApiConstants.SUCCESS.toString() && it.obj.isNotEmpty()) {
                binding.emptyLayout.txtEmpty.visibility = View.GONE
               allItems.addAll(it.obj)
                items.addAll(it.obj)
             //  PrefUtils.saveObjectValue(this, PrefUtils.item_all, items)

                adapter.setItems(items)
                if (selectedIds.isNotEmpty()) {
                    items = arrayListOf()
                    for (item in allItems) {
                        if (selectedIds.contains(item.CategoryID)) {
                            items.add(item)
                        }
                    }
                    adapter.setItems(items)
                }
                refreshCart()
            } else {
                binding.emptyLayout.txtEmpty.visibility = View.VISIBLE
                binding.emptyLayout.txtEmpty.text = "No products"
            }
        })
    }

    private fun refreshCart() {

        FoodDBHelper.getCartItems(this, object : FoodDBHelper.RoomCallBack {
            override fun onItem(item: SubItem) {
                // do nothing
            }

            override fun onNotExist() {
                binding.fabProceed.visibility = View.GONE
            }

            override fun onAllItems(items: ArrayList<SubItem>) {
                if (items.isNotEmpty()) {
                    binding.fabProceed.visibility = View.VISIBLE
                } else {
                    binding.fabProceed.visibility = View.GONE
                }
            }
        })
    }

    private fun initClick() {
        binding.toolbarLayout.imgProfile.setOnClickListener { fireIntent(ProfileActivity::class.java) }
        binding.toolbarLayout.llamountbal.setOnClickListener {

            val intent = Intent(this, PaymentActivity::class.java);
            intent.putExtra("bal", bal);
            startActivity(intent)
        }
        binding.toolbarLayout.llamountbal.visibility = View.VISIBLE
        binding.toolbarLayout.notification.visibility = View.VISIBLE
        binding.toolbarLayout.search.visibility = View.VISIBLE
        binding.toolbarLayout.notification.setOnClickListener { fireIntent(NotificationActivity::class.java)
            saveStringValue(this, "NOTIFICATION_COUNT", "")
        }
        binding.toolbarLayout.search.setOnClickListener { fireIntent(SearchProductActivity::class.java) }

        binding.fabProceed.setOnClickListener { fireIntent(CartActivity::class.java) }
        binding.toolbarLayout.imgcall.setOnClickListener {
            callIntent(startUp.HelpLineNo)
        }
        binding.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {
                adapter.getFilter().filter(editable.toString())
            }
        })
    }
    private fun addAutoStartup() {
        try {
            var isDisplay = false
            val manufacturer1 = Build.MANUFACTURER
            if (manufacturer1.equals("xiaomi", ignoreCase = true) ||
                manufacturer1.equals("oppo", ignoreCase = true) ||
                manufacturer1.equals("vivo", ignoreCase = true) ||
                manufacturer1.equals("Letv", ignoreCase = true) ||
                manufacturer1.equals(
                    "Honor",
                    ignoreCase = true
                ) /*manufacturer1.equalsIgnoreCase("Realme") ||*/) {
                isDisplay = true
            }
            if (isDisplay) {
                showAlert("App permission",
                    "To receive timely OFFERS, please enable the auto start feature.",
                    "Continue",
                    "",
                    true,
                    object : OnOptionListener {
                        override fun onClick(isYes: Boolean) {
                            if (isYes) {
                                try {
                                    val intent = Intent()
                                    val manufacturer = Build.MANUFACTURER
                                    if ("xiaomi".equals(manufacturer, ignoreCase = true)) {
                                        intent.component = ComponentName(
                                            "com.miui.securitycenter",
                                            "com.miui.permcenter.autostart.AutoStartManagementActivity"
                                        )
                                    } else if ("oppo".equals(manufacturer, ignoreCase = true)) {
                                        intent.component = ComponentName(
                                            "com.coloros.safecenter",
                                            "com.coloros.safecenter.permission.startup.StartupAppListActivity"
                                        )
                                    } else if ("vivo".equals(manufacturer, ignoreCase = true)) {
                                        intent.component = ComponentName(
                                            "com.vivo.permissionmanager",
                                            "com.vivo.permissionmanager.activity.BgStartUpManagerActivity"
                                        )
                                    } else if ("Letv".equals(manufacturer, ignoreCase = true)) {
                                        intent.component = ComponentName(
                                            "com.letv.android.letvsafe",
                                            "com.letv.android.letvsafe.AutobootManageActivity"
                                        )
                                    } else if ("Honor".equals(manufacturer, ignoreCase = true)) {
                                        intent.component = ComponentName(
                                            "com.huawei.systemmanager",
                                            "com.huawei.systemmanager.optimize.process.ProtectActivity"
                                        )
                                    } else if ("oneplus".equals(manufacturer, ignoreCase = true)) {
                                        intent.component = ComponentName(
                                            "com.oneplus.security",
                                            "com.oneplus.security.chainlaunch.view.ChainLaunchAppListAct‌​ivity"
                                        )
                                    }
                                    //                                    else if ("realme".equalsIgnoreCase(manufacturer)) {
//                                        intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                                        intent.setData(Uri.parse("package:" + "com.taazafood"));
//                                    }
                                    val list = packageManager.queryIntentActivities(
                                        intent,
                                        PackageManager.MATCH_DEFAULT_ONLY
                                    )
                                    if (list.size > 0) {
                                        startActivity(intent)
                                    }
                                } catch (ex: java.lang.Exception) {

                                }
                            }
                        }
                    })
            }


        } catch (e: java.lang.Exception) {
            Log.e("exc", e.toString())
        }


    }

    private fun getwallet()
    {
        ApiClient(this).getWallet().observe(this as AppCompatActivity, {
            if (it.response_code == ApiConstants.SUCCESS.toString()) {
                var res = it.obj.Balance
                binding.toolbarLayout.llamountbal.visibility = View.VISIBLE
                binding.toolbarLayout.amountbal.text ="₹ "+res;
                bal= res


            }
        })
    }
    override fun update() {
        refreshCart()
        setUserDetails();
    }
    override fun onResume() {
        super.onResume()
        getwallet()
        refreshCart()
        binding.rvItems.adapter = adapter

        setUserDetails()

    }
    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
    override fun getSelected(categories: ArrayList<Int>) {
        selectedIds = arrayListOf()
        selectedIds.addAll(categories)
        if (selectedIds.isEmpty()) {
            items.addAll(allItems)
            adapter.setItems(allItems)
        } else {
            items = arrayListOf()
            for (item in allItems) {
                if (selectedIds.contains(item.CategoryID)) {
                    items.add(item)
                }
            }
            adapter.setItems(items)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_dashboard, menu)
        return false
    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        displayScreen(item.itemId)

        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
    fun GetAppVersion(context: Context): String {
        var version = ""
        try {
            val pInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            version =  pInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return version
    }
}