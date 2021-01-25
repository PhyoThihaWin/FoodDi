package com.pthw.food.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.pthw.food.BuildConfig
import com.pthw.food.R
import com.pthw.food.utils.ConstantValue
import com.pthw.food.utils.PrefManager
import kotlinx.android.synthetic.main.activity_start.*
import java.util.*

class StartActivity : AppCompatActivity() {

    lateinit var prefManager: PrefManager
    val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        setContentView(R.layout.activity_start)

        prefManager = PrefManager(this)
        ConstantValue.lang = prefManager.getLanguage()

        txt_version.text = "Version ${BuildConfig.VERSION_NAME}"
        NextPage()
    }


    private fun NextPage() {
        var i = 0
        runOnUiThread(object : Runnable {
            override fun run() {
                if (i < 2) {
                    i++
                    handler.postDelayed(this, 1000)
                } else {
                    handler.removeMessages(0)
                    finish()
                    startActivity(Intent(this@StartActivity, MainActivity::class.java))
                }
                Log.i("myHandler", "myhandler")
            }
        })
    }


//    private fun getToken(value: String): String {
//        val bytes = value.toByteArray()
//        val md = MessageDigest.getInstance("SHA-256")
//        val digest = md.digest(bytes)
//        return digest.fold("", { str, it -> str + "%02x".format(it) })
//    }

}
