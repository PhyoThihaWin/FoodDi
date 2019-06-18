package com.pthw.food

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.full_screen_dialog.view.*


class FullScreenDialog : DialogFragment() {


    companion object {
        var TAG = "FullScreenDialog"
        var language = arrayOf("Zawgyi", "Unicode", "English")
        lateinit var sharedPreference: SharedPreferences
    }

    var lang: String? = null

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

        //toolbar.setTitleTextColor(Color.WHITE)

        view.txtChooseLanguage.setOnClickListener {
            val alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(context!!)
            alertDialogBuilder.setItems(language) { dialog, which ->
                var editor = sharedPreference.edit()
                when (which) {
                    0 -> editor.putString("language", "zawgyi")
                    1 -> editor.putString("language", "unicode")
                    2 -> editor.putString("language", "english")
                }
                editor.commit()
                dismiss()
                activity!!.recreate()

            }
            alertDialogBuilder.show()

        }



        return view
    }


    fun setLanguage(view: View, lang: String) {
        when (lang) {
            "unicode" -> {
                view.toolbar.setTitle(R.string.setting)
                view.txtChooseLanguage.setText(R.string.chooseLanguage)
                view.txtAddFood.setText(R.string.newFood)
                view.txtAboutApp.setText(R.string.aboutApp)
            }
            "zawgyi" -> {
                view.toolbar.setTitle(R.string.settingZ)
                view.txtChooseLanguage.setText(R.string.chooseLanguageZ)
                view.txtAddFood.setText(R.string.newFoodZ)
                view.txtAboutApp.setText(R.string.aboutAppZ)
            }
            else -> {
                view.toolbar.setTitle(R.string.settingE)
                view.txtChooseLanguage.setText(R.string.chooseLanguageE)
                view.txtAddFood.setText(R.string.newFoodE)
                view.txtAboutApp.setText(R.string.aboutAppE)
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
