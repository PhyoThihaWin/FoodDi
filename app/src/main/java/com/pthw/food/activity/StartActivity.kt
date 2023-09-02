package com.pthw.food.activity

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.pthw.food.BuildConfig
import com.pthw.food.R
import com.pthw.food.base.BaseActivity
import com.pthw.food.databinding.ActivityStartBinding
import com.pthw.food.utils.ConstantValue
import com.pthw.food.utils.PrefManager
import com.pthw.food.utils.inflater
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class StartActivity : BaseActivity<ActivityStartBinding>() {

    override val binding: ActivityStartBinding by lazy {
        ActivityStartBinding.inflate(inflater())
    }

    private lateinit var prefManager: PrefManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        setContentView(R.layout.activity_start)

        prefManager = PrefManager(this)
        ConstantValue.lang = prefManager.getLanguage()

        binding.txtVersion.text = "Version ${BuildConfig.VERSION_NAME}"
        nextPage()
    }


    private fun nextPage() {
        lifecycleScope.launch {
            delay(800)
            startActivity(Intent(this@StartActivity, MainActivity::class.java))
            finish()
        }
    }

}
