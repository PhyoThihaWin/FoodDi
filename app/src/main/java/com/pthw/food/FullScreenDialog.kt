package com.pthw.food

import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.pthw.food.activity.StartActivity
import com.pthw.food.utils.ConstantValue
import com.pthw.food.utils.PrefManager
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.custom_dialog.view.*
import kotlinx.android.synthetic.main.full_screen_dialog.view.*


class FullScreenDialog : DialogFragment() {


    companion object {
        var TAG = "FullScreenDialog"
    }

    var languageArr = emptyArray<String>()
    lateinit var mAdView: AdView
    lateinit var prefManager: PrefManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialogStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.full_screen_dialog, container, false)

        view.toolbar.setNavigationIcon(R.drawable.ic_close_white)
        view.toolbar.setNavigationOnClickListener { dismiss() }
        view.toolbar.setTitleTextColor(resources.getColor(R.color.colorPrimary))

        prefManager = PrefManager(context!!)

        setLanguage(view, ConstantValue.lang)

        languageArr = when (ConstantValue.lang) {
            "unicode" -> activity!!.resources.getStringArray(R.array.languageUni)
            "zawgyi" -> activity!!.resources.getStringArray(R.array.languageZaw)
            else -> activity!!.resources.getStringArray(R.array.languageEn)
        }

        setupGoogleAd(view)


        view.txtChooseLanguage.setOnClickListener { showLangDialog() }


        view.txtAboutApp.setOnClickListener {
            val mDialogView = LayoutInflater.from(context).inflate(R.layout.custom_dialog, null)
            val mBuilder =
                AlertDialog.Builder(context!!, R.style.customizedAlert).setView(mDialogView)
            Picasso.get().load(R.drawable.fbad).fit().centerCrop()
                .placeholder(R.drawable.logoblack).into(mDialogView.img)
            mBuilder.show()
        }

        view.txtMoreApp.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/dev?id=5729357381500909341")
                )
            )
        }

        return view
    }

    private fun setupGoogleAd(view: View) {
        MobileAds.initialize(context, getString(R.string.App_id)) //--load ads
        //--ad banner
        mAdView = view.findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
    }


    private fun showLangDialog() {
        val checkItem = when (ConstantValue.lang) {
            "zawgyi" -> 2
            "unicode" -> 1
            else -> 0
        }
        val builder = AlertDialog.Builder(context!!, R.style.customizedAlert)

        builder.setSingleChoiceItems(
            ArrayAdapter(context!!, R.layout.rtl_radio_item, languageArr), checkItem
        ) { dialogInterface: DialogInterface, i: Int ->
            when (i) {
                0 -> prefManager.setLanguage("english")
                1 -> prefManager.setLanguage("unicode")
                2 -> prefManager.setLanguage("zawgyi")
            }

            if (checkItem != i) {
                dialogInterface.dismiss()
                activity!!.finish()
                startActivity(Intent(activity, StartActivity::class.java))
            } else dialogInterface.dismiss()
        }
        builder.show()
    }


    fun setLanguage(view: View, lang: String) {
        when (lang) {
            "unicode" -> {
                view.toolbar.setTitle(R.string.setting)
                view.txtChooseLanguage.setText(R.string.chooseLanguage)
                view.txtAboutApp.setText(R.string.aboutApp)
                view.txtMoreApp.setText(R.string.moreApp)
            }
            "zawgyi" -> {
                view.toolbar.setTitle(R.string.settingZ)
                view.txtChooseLanguage.setText(R.string.chooseLanguageZ)
                view.txtAboutApp.setText(R.string.aboutAppZ)
                view.txtMoreApp.setText(R.string.moreAppZ)
            }
            else -> {
                view.toolbar.setTitle(R.string.settingE)
                view.txtChooseLanguage.setText(R.string.chooseLanguageE)
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
