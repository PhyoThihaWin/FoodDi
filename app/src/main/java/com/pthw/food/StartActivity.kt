package com.pthw.food

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import com.developer.pthw.retrofittest.Api.Api
import com.developer.pthw.retrofittest.Api.ApiClient
import kotlinx.android.synthetic.main.activity_start.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class StartActivity : AppCompatActivity() {

    lateinit var sharedPreference: SharedPreferences
    var lang: String? = null

    val api: Api = ApiClient.client.create(Api::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)


        sharedPreference = getSharedPreferences("myfile", Context.MODE_PRIVATE)
        lang = sharedPreference.getString("language", "")

        checkUpdate()

        btnUnicode.setOnClickListener {
            var editor = sharedPreference.edit()
            editor.putString("language", "unicode")
            editor.commit()

            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("language", "unicode")
            startActivity(intent)
            finish()
        }
        btnZawgyi.setOnClickListener {
            var editor = sharedPreference.edit()
            editor.putString("language", "zawgyi")
            editor.commit()

            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("language", "zawgyi")
            startActivity(intent)
            finish()
        }
    }

    fun checkUpdate() {

        progress_circular.visibility = View.VISIBLE

        val call = api.checkUpdate("version")
        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {

                if (response.body().equals(BuildConfig.VERSION_NAME) && lang != "") {
                    val intent = Intent(this@StartActivity, MainActivity::class.java)
                    intent.putExtra("language", lang)
                    startActivity(intent)
                    finish()
                } else if (!response.body().equals(BuildConfig.VERSION_NAME)) {
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://play.google.com/store/apps/details?id=" + packageName)
                        )
                    )
                    progress_circular.visibility = View.INVISIBLE
                } else {
                    progress_circular.visibility = View.INVISIBLE
                    viewHide(false)
                }


            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                progress_circular.visibility = View.INVISIBLE
                if (t is IOException) {
                    var str: String
                    if (t is IOException)
                        str = "No Internet Connection!                                      "
                    else str = "Something went wrong"

                    val dialogBuilder = AlertDialog.Builder(this@StartActivity, R.style.customizedAlert)
                    dialogBuilder.setMessage(str).setCancelable(false)
                        .setPositiveButton("Retry") { dialog, id ->
                            checkUpdate() //--recall chekupdate function to reconnect
                        }.create().show()
                } else Log.i("Retrofit error", t.message)
                viewHide(true)
            }
        })
    }

    fun viewHide(f: Boolean) {
        if (f) {
            linearLayout2.visibility = View.INVISIBLE
            cc.visibility = View.INVISIBLE
        } else {

            val animation = AnimationUtils.loadAnimation(this@StartActivity, R.anim.dialog_enter)

            linearLayout2.visibility = View.VISIBLE
            cc.visibility = View.VISIBLE

            linearLayout2.startAnimation(animation)
            cc.startAnimation(animation)
        }
    }

}
