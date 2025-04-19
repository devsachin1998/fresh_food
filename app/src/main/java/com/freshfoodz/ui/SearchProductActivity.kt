package com.freshfoodz.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.freshfoodz.R
import com.freshfoodz.adapter.CategoryAdapter
import com.freshfoodz.adapter.StockListAdapter
import com.freshfoodz.api.ApiClient
import com.freshfoodz.api.ApiConstants
import com.freshfoodz.databinding.ActivitySearchProductBinding
import com.freshfoodz.helper.PrefUtils
import com.freshfoodz.helper.fireIntent
import com.freshfoodz.model.StartUpResponse
import com.freshfoodz.model.StockListResponse
import com.freshfoodz.model.SubItem
import com.freshfoodz.room.FoodDBHelper
import com.google.android.flexbox.*

class SearchProductActivity : AppCompatActivity(), StockListAdapter.OnUpdateListener,
    CategoryAdapter.OnCategorySelectionListener {

    private lateinit var binding: ActivitySearchProductBinding
    private lateinit var adapter: StockListAdapter
    private lateinit var catAdapter: CategoryAdapter
    private lateinit var startUp: StartUpResponse.StartUp
    private lateinit var getitem: Array<StockListResponse.Item>
    private var items = arrayListOf<StockListResponse.Item>()
    private var allItems = arrayListOf<StockListResponse.Item>()
    private var selectedIds = arrayListOf<Int>()
    private var bal = 0.0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_product)
        setSupportActionBar(binding.toolbarLayout.toolbar)
        init()
        initClick()
        // Update()



        //  txtname = (header.findViewById<View>(R.id.txtname) as TextView).toString()

        adapter = StockListAdapter(this)

        startUp = PrefUtils.getStartUp(this)

        // val toJson = MyApplication.gson!!.toJson(z)


        if (startUp._categoryList.isNotEmpty()) {
            catAdapter = CategoryAdapter(startUp._categoryList, this)
            binding.rvCategories.visibility = View.GONE
            binding.rvCategories.setLayoutManager(
                LinearLayoutManager(
                    this,
                    LinearLayoutManager.HORIZONTAL,
                    true
                )
            )

            val layoutManager = FlexboxLayoutManager(this)
            layoutManager.flexDirection = FlexDirection.ROW
            layoutManager.flexWrap = FlexWrap.WRAP
            layoutManager.justifyContent = JustifyContent.FLEX_START
            layoutManager.alignItems = AlignItems.FLEX_START
            binding.rvCategories.layoutManager = layoutManager

            binding.rvCategories.adapter = catAdapter
        }
        binding.rvItems.adapter = adapter

        getitem();
        // getlocal();


    }


    override fun onStart() {
        super.onStart()


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


    private fun init() {

        setSupportActionBar(binding.toolbarLayout.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbarLayout.toolbar.title = "Search"
        binding.toolbarLayout.imgcall.visibility = View.GONE
        binding.fabProceed.setOnClickListener { fireIntent(CartActivity::class.java) }

        binding.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {
                adapter.getFilter().filter(editable.toString())
            }
        })


        binding.searchcancle.setOnClickListener { binding.edtSearch.setText("") }
        refreshCart()

    }

    private fun clearSession() {
        PrefUtils.saveBoolean(this, PrefUtils.IS_LOGGED_IN, false)
        FoodDBHelper.clearCart(this)
        fireIntent(SplashActivity::class.java)
    }

    private fun getitem() {
        binding.edtSearch.setText("")
        items = arrayListOf()
        allItems = arrayListOf()
        ApiClient(this).getStockList().observe(this, {
            if (it.response_code == ApiConstants.SUCCESS.toString() && it.obj.isNotEmpty()) {
                binding.emptyLayout.txtEmpty.visibility = View.GONE
                allItems.addAll(it.obj)
                items.addAll(it.obj)
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
        binding.toolbarLayout.toolbar.setNavigationOnClickListener {

            fireIntent(DashboardActivity::class.java, true)
        }


    }

    override fun update() {
        refreshCart()
        //setUserDetails();
    }

    override fun onResume() {
        super.onResume()
        //getwallet()
        refreshCart()
        binding.rvItems.adapter = adapter


    }

    override fun onBackPressed() {

        fireIntent(DashboardActivity::class.java, true)

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
}

