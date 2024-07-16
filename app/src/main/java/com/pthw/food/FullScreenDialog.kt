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
import com.pthw.food.activity.StartActivity
import com.pthw.food.base.BaseDialogFragment
import com.pthw.food.databinding.CustomDialogBinding
import com.pthw.food.databinding.FullScreenDialogBinding
import com.pthw.food.utils.ConstantValue
import com.pthw.food.utils.PrefManager
import com.pthw.food.utils.inflater
import com.squareup.picasso.Picasso


class FullScreenDialog : BaseDialogFragment<FullScreenDialogBinding>() {
    override fun bindView(inflater: LayoutInflater): FullScreenDialogBinding {
        return FullScreenDialogBinding.inflate(inflater)
    }

    companion object {
        var TAG = "FullScreenDialog"
    }

    var languageArr = emptyArray<String>()
    lateinit var prefManager: PrefManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialogStyle)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationIcon(R.drawable.ic_close_white)
        binding.toolbar.setNavigationOnClickListener { dismiss() }
        binding.toolbar.setTitleTextColor(resources.getColor(R.color.colorPrimary))

        prefManager = PrefManager(mContext)

        setLanguage(view, ConstantValue.lang)

        languageArr = when (ConstantValue.lang) {
            "unicode" -> mContext.resources.getStringArray(R.array.languageUni)
            "zawgyi" -> mContext.resources.getStringArray(R.array.languageZaw)
            else -> mContext.resources.getStringArray(R.array.languageEn)
        }


        binding.txtChooseLanguage.setOnClickListener { showLangDialog() }


        binding.txtAboutApp.setOnClickListener {
            val mDialogBinding = CustomDialogBinding.inflate(mContext.inflater())
            val mBuilder = AlertDialog.Builder(mContext, R.style.customizedAlert)
            mBuilder.setView(mDialogBinding.root)
            Picasso.get()
                .load(R.drawable.fbad).fit().centerCrop()
                .placeholder(R.drawable.logoblack)
                .into(mDialogBinding.img)
            mBuilder.show()
        }

        binding.txtMoreApp.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/dev?id=5729357381500909341")
                )
            )
        }
    }


    private fun showLangDialog() {
        val checkItem = when (ConstantValue.lang) {
            "zawgyi" -> 2
            "unicode" -> 1
            else -> 0
        }
        val builder = AlertDialog.Builder(mContext, R.style.customizedAlert)

        builder.setSingleChoiceItems(
            ArrayAdapter(mContext, R.layout.rtl_radio_item, languageArr), checkItem
        ) { dialogInterface: DialogInterface, i: Int ->
            when (i) {
                0 -> prefManager.setLanguage("english")
                1 -> prefManager.setLanguage("unicode")
                2 -> prefManager.setLanguage("zawgyi")
            }

            if (checkItem != i) {
                dialogInterface.dismiss()
                mActivity.finish()
                startActivity(Intent(activity, StartActivity::class.java))
            } else dialogInterface.dismiss()
        }
        builder.show()
    }


    fun setLanguage(view: View, lang: String) {
        when (lang) {
            "unicode" -> {
                binding.toolbar.setTitle(R.string.setting)
                binding.txtChooseLanguage.setText(R.string.chooseLanguage)
                binding.txtAboutApp.setText(R.string.aboutApp)
                binding.txtMoreApp.setText(R.string.moreApp)
            }

            "zawgyi" -> {
                binding.toolbar.setTitle(R.string.settingZ)
                binding.txtChooseLanguage.setText(R.string.chooseLanguageZ)
                binding.txtAboutApp.setText(R.string.aboutAppZ)
                binding.txtMoreApp.setText(R.string.moreAppZ)
            }

            else -> {
                binding.toolbar.setTitle(R.string.settingE)
                binding.txtChooseLanguage.setText(R.string.chooseLanguageE)
                binding.txtAboutApp.setText(R.string.aboutAppE)
                binding.txtMoreApp.setText(R.string.moreAppE)
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
