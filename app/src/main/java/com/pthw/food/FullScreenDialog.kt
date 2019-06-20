package com.pthw.food

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.developer.pthw.retrofittest.Api.ApiClient
import com.google.android.gms.ads.*
import kotlinx.android.synthetic.main.custom_dialog.view.*
import kotlinx.android.synthetic.main.full_screen_dialog.view.*


class FullScreenDialog : DialogFragment() {


    companion object {
        var TAG = "FullScreenDialog"
        var language = arrayOf("Zawgyi", "Unicode", "English")
        lateinit var sharedPreference: SharedPreferences
    }

    var lang: String? = null

    //ads
    lateinit var mAdView: AdView
    private lateinit var mInterstitialAd: InterstitialAd

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.full_screen_dialog, container, false)

        sharedPreference = context!!.getSharedPreferences("myfile", Context.MODE_PRIVATE)
        lang = sharedPreference.getString("language", "")

        view.toolbar.setNavigationIcon(R.drawable.ic_close_white)
        view.toolbar.setNavigationOnClickListener { dismiss() }
        setLanguage(view, lang!!)

        MobileAds.initialize(context, getString(R.string.App_id)) //--load ads
        //--ad banner
        mAdView = view.findViewById(R.id.adView)
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
                mAdView.loadAd(adRequest)
            }
        }

        view.txtChooseLanguage.setOnClickListener {

            // loadAds()

            val alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(context!!, R.style.customizedAlert)
            alertDialogBuilder.setItems(language) { dialog, which ->
                var editor = sharedPreference.edit()
                when (which) {
                    0 -> editor.putString("language", "zawgyi")
                    1 -> editor.putString("language", "unicode")
                    2 -> editor.putString("language", "english")
                }
                editor.commit()
                dismiss()
                activity!!.finish()
                startActivity(Intent(activity, StartActivity::class.java))


            }
            alertDialogBuilder.show()

        }

        view.txtAddFood.setOnClickListener {
            Toast.makeText(context, "Coming soon feature !", Toast.LENGTH_SHORT).show()
        }

        view.txtAboutApp.setOnClickListener {

            //---cusotm round dialog

            val mDialogView = LayoutInflater.from(context).inflate(R.layout.custom_dialog, null)
            //AlertDialogBuilder
            val mBuilder = AlertDialog.Builder(context!!, R.style.customizedAlert)
                .setView(mDialogView)
            Glide.with(context!!).load(ApiClient.BASE_URL + "fbad.png")
                .apply(RequestOptions().placeholder(R.drawable.logoblack))
                .into(mDialogView.img)
            //show dialog
            mBuilder.show()

        }

        view.txtMoreApp.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/developer?id=MIN+HTOO+TINT+AUNG")
                )
            )
        }

        return view
    }

//    fun loadAds() {
//        //--load interstitial ads
//        if (mInterstitialAd.isLoaded) {
//            mInterstitialAd.show()
//        } else {
//            Log.d("TAG", "The interstitial wasn't loaded yet.")
//        }
//    }


    fun setLanguage(view: View, lang: String) {
        when (lang) {
            "unicode" -> {
                view.toolbar.setTitle(R.string.setting)
                view.txtChooseLanguage.setText(R.string.chooseLanguage)
                view.txtAddFood.setText(R.string.newFood)
                view.txtAboutApp.setText(R.string.aboutApp)
                view.txtMoreApp.setText(R.string.moreApp)
            }
            "zawgyi" -> {
                view.toolbar.setTitle(R.string.settingZ)
                view.txtChooseLanguage.setText(R.string.chooseLanguageZ)
                view.txtAddFood.setText(R.string.newFoodZ)
                view.txtAboutApp.setText(R.string.aboutAppZ)
                view.txtMoreApp.setText(R.string.moreAppZ)
            }
            else -> {
                view.toolbar.setTitle(R.string.settingE)
                view.txtChooseLanguage.setText(R.string.chooseLanguageE)
                view.txtAddFood.setText(R.string.newFoodE)
                view.txtAboutApp.setText(R.string.aboutAppE)
                view.txtMoreApp.setText(R.string.moreAppE)
            }
        }
    }


    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT

            //---animate the full screen dialog---//
            dialog.window!!.attributes.windowAnimations = R.style.DialogThemeAnim
            dialog.window!!.setLayout(width, height)
        }
    }
}
