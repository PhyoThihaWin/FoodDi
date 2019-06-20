package com.pthw.food

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView.LayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.developer.pthw.retrofittest.Api.Api
import com.developer.pthw.retrofittest.Api.ApiClient
import com.google.android.gms.ads.*
import com.pthw.food.adapter.ItemAdapter
import com.pthw.food.model.Food
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.recycler_content.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class MainActivity : AppCompatActivity() {

    lateinit var loading: ProgressDialog
    val api: Api = ApiClient.client.create(Api::class.java)

    lateinit var itemAdapter: ItemAdapter
    lateinit var foodList: List<Food>

    lateinit var lang: String
    // lateinit var sharedPreference: SharedPreferences

    //ads
    lateinit var mAdView: AdView
    private lateinit var mInterstitialAd: InterstitialAd


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN) //--show keyboard without layout move up
        loading = ProgressDialog(this, R.style.customizedAlert)
        loading.setCancelable(false)

        //--to check zawgyi unicode
        //--sharedPreference = getSharedPreferences("myfile", Context.MODE_PRIVATE)
        lang = intent.getStringExtra("language")


        MobileAds.initialize(this, getString(R.string.App_id)) //--load ads
        //--ad banner
        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
        mAdView.adListener = object : AdListener() {
            override fun onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            override fun onAdFailedToLoad(errorCode: Int) {
                // Code to be executed when an ad request fails.
            }

            override fun onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            override fun onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            override fun onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            override fun onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
                mAdView.loadAd(adRequest)
            }
        }

        //--ad interstitial
        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId = getString(R.string.FullAd_id)
        mInterstitialAd.loadAd(AdRequest.Builder().build())

        mInterstitialAd.adListener = object : AdListener() {
            override fun onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            override fun onAdFailedToLoad(errorCode: Int) {
                // Code to be executed when an ad request fails.
            }

            override fun onAdOpened() {
                // Code to be executed when the ad is displayed.
            }

            override fun onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            override fun onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            override fun onAdClosed() {
                // Code to be executed when the interstitial ad is closed.
                mInterstitialAd.loadAd(AdRequest.Builder().build())
            }
        }




        recycler.setHasFixedSize(true)
        var mLayoutManager: LayoutManager = LinearLayoutManager(this)
        recycler.layoutManager = mLayoutManager

        getFoods() //--recycler first bind from api

        //--recycler start position
        imgSearch.setOnClickListener {
            recycler.smoothScrollToPosition(0)
            motionlayout.transitionToStart()
        }

        fullMenu.setOnClickListener {
            val dialog = FullScreenDialog()
            val ft = supportFragmentManager.beginTransaction()
            dialog.show(ft, FullScreenDialog.TAG)
        }

        //--search edit text
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (etSearch.length() >= 2)
                    imgDeleteSearch.visibility = View.VISIBLE
                else imgDeleteSearch.visibility = View.INVISIBLE
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        //--Set an item click listener for auto complete text view
        etSearch.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position).toString()
            etSearch.hideKeyboard()
            getSearchItem(selectedItem)
        }


        //--cross-image to delete search
        imgDeleteSearch.setOnClickListener {
            etSearch.setText("")
        }

        txtRetry.setOnClickListener {
            mAdView.loadAd(adRequest)
            getFoods()
        }
    }


    //--hide keyboard method
    fun View.hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    fun getFoods() {

        etSearch.setText("")//--clear text
        loading.show()
        foodList = emptyList()

        val call = api.getFoods("select")
        call.enqueue(object : Callback<List<Food>> {
            override fun onResponse(call: Call<List<Food>>, response: Response<List<Food>>) {
                showNoInternet(false) //--hide no connection layout
                foodList = response.body()!!
                //displaying the string array into listview
                Log.i("ssss", response.body().toString())
                itemAdapter = ItemAdapter(this@MainActivity, foodList, lang)
                recycler!!.adapter = itemAdapter
                itemAdapter.notifyDataSetChanged()

                getArray(foodList)

                loading.dismiss()
            }

            override fun onFailure(call: Call<List<Food>>, t: Throwable) {
                if (t is IOException) {
                    showNoInternet(true)
                    itemAdapter = ItemAdapter(this@MainActivity, foodList, lang)
                    recycler.adapter = itemAdapter
                } else Log.i("Retrofit error", t.message)
                loading.dismiss()

            }
        })
    }

    fun getSearchItem(word: String) {

        loading.show()
        foodList = emptyList()

        val call = api.getSearchItem("search", word)
        call.enqueue(object : Callback<List<Food>> {
            override fun onResponse(call: Call<List<Food>>, response: Response<List<Food>>) {
                showNoInternet(false) //--hide no connection layout
                foodList = response.body()!!
                //displaying the string array into listview
                itemAdapter = ItemAdapter(this@MainActivity, foodList, lang)
                recycler!!.adapter = itemAdapter
                itemAdapter.notifyDataSetChanged()

                loading.dismiss()
            }

            override fun onFailure(call: Call<List<Food>>, t: Throwable) {
                if (t is IOException) {
                    showNoInternet(true)
                    itemAdapter = ItemAdapter(this@MainActivity, foodList, lang)
                    recycler.adapter = itemAdapter
                } else Log.i("Retrofit error", t.message)
                loading.dismiss()

            }
        })
    }

    fun getFilterItem(item: String) {

        //--load interstitial ads
        if (mInterstitialAd.isLoaded) {
            mInterstitialAd.show()
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.")
        }

        etSearch.setText("")//--clear text
        loading.show()
        foodList = emptyList()

        val call = api.getFilterItem("filter", item)
        call.enqueue(object : Callback<List<Food>> {
            override fun onResponse(call: Call<List<Food>>, response: Response<List<Food>>) {
                showNoInternet(false) //--hide no connection layout
                foodList = response.body()!!
                itemAdapter = ItemAdapter(this@MainActivity, foodList, lang)
                recycler!!.adapter = itemAdapter
                loading.dismiss()
            }

            override fun onFailure(call: Call<List<Food>>, t: Throwable) {
                if (t is IOException) {
                    showNoInternet(true)
                    itemAdapter = ItemAdapter(this@MainActivity, foodList, lang)
                    recycler.adapter = itemAdapter

                } else Log.i("Retrofit error", t.message)
                loading.dismiss()

            }
        })
    }

    // convert object list ot string array for autocomplete textview
    fun getArray(f: List<Food>) {
        var k = 0
        val str = arrayOfNulls<String>(f.size * 2)
        for (i in 0..f.size - 1) {
            when (lang) {
                "unicode" -> {
                    str.set(k, f.get(i).oneMM)
                    str.set(k + 1, f.get(i).twoMM)
                }
                "zawgyi" -> {
                    str.set(k, f.get(i).oneZ)
                    str.set(k + 1, f.get(i).twoZ)
                }
                "english" -> {
                    str.set(k, f.get(i).oneEN)
                    str.set(k + 1, f.get(i).twoEN)
                }
                else -> {
                    str.set(k, f.get(i).oneZ)
                    str.set(k + 1, f.get(i).twoZ)
                }
            }
            k += 2
        }

        //set adapter for autocomplete textview
        var adapter = ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, str)
        etSearch.setAdapter(adapter)
    }

    fun showNoInternet(f: Boolean) {
        if (f) layout_connection.visibility = View.VISIBLE
        else layout_connection.visibility = View.INVISIBLE
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    //--set menu item title at activity runtime
    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        when (lang) {
            "unicode" -> {
                etSearch.setHint(R.string.search)
                menu.findItem(R.id.all).setTitle(R.string.all)
                menu.findItem(R.id.food).setTitle(R.string.food)
                menu.findItem(R.id.fruit).setTitle(R.string.fruit)
                menu.findItem(R.id.vegetable).setTitle(R.string.vegetable)
                menu.findItem(R.id.meat).setTitle(R.string.meat)
                menu.findItem(R.id.snack).setTitle(R.string.snack)
                loading.setMessage(getString(R.string.data))
                txtNoInternet.setText(R.string.connection)
                txtRetry.setText(R.string.retry)

            }
            "zawgyi" -> {
                etSearch.setHint(R.string.searchZ)
                menu.findItem(R.id.all).setTitle(R.string.allZ)
                menu.findItem(R.id.food).setTitle(R.string.foodZ)
                menu.findItem(R.id.fruit).setTitle(R.string.fruitZ)
                menu.findItem(R.id.vegetable).setTitle(R.string.vegetableZ)
                menu.findItem(R.id.meat).setTitle(R.string.meatZ)
                menu.findItem(R.id.snack).setTitle(R.string.snackZ)
                loading.setMessage(getString(R.string.dataZ))
                txtNoInternet.setText(R.string.connectionZ)
                txtRetry.setText(R.string.retryZ)
            }
            else -> {
                etSearch.setHint(R.string.searchE)
                menu.findItem(R.id.all).setTitle(R.string.allE)
                menu.findItem(R.id.food).setTitle(R.string.foodE)
                menu.findItem(R.id.fruit).setTitle(R.string.fruitE)
                menu.findItem(R.id.vegetable).setTitle(R.string.vegetableE)
                menu.findItem(R.id.meat).setTitle(R.string.meatE)
                menu.findItem(R.id.snack).setTitle(R.string.snackE)
                loading.setMessage(getString(R.string.dataE))
                txtNoInternet.setText(R.string.connectionE)
                txtRetry.setText(R.string.retryE)
            }
        }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item!!.itemId) {
            R.id.all -> {
                getFoods()
                seasonal.setText(R.string.app_name)
            }
            R.id.food -> {
                getFilterItem("food")
                seasonal.setText(item.title)
            }
            R.id.fruit -> {
                getFilterItem("fruit")
                seasonal.setText(item.title)
            }
            R.id.vegetable -> {
                getFilterItem("vegetable")
                seasonal.setText(item.title)
            }
            R.id.meat -> {
                getFilterItem("meat")
                seasonal.setText(item.title)
            }
            R.id.snack -> {
                getFilterItem("snack")
                seasonal.setText(item.title)
            }
        }

        etSearch.hideKeyboard()
        return super.onOptionsItemSelected(item)
    }
}
