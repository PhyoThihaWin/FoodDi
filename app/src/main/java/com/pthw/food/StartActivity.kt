package com.pthw.food

import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.developer.pthw.retrofittest.Api.Api
import com.developer.pthw.retrofittest.Api.ApiClient
import com.pthw.food.adapter.ItemAdapter
import com.pthw.food.model.Food
import kotlinx.android.synthetic.main.activity_start.*
import kotlinx.android.synthetic.main.recycler_content.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class StartActivity : AppCompatActivity() {

    lateinit var sharedPreference: SharedPreferences
    var lang: String? = null

    val api: Api = ApiClient.client.create(Api::class.java)
    var loading: ProgressDialog? = null

    var available: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        sharedPreference = getSharedPreferences("myfile", Context.MODE_PRIVATE)
        lang = sharedPreference.getString("language", "")

//        if (lang != "") {
//            viewHide(true)
//        } else {
//            viewHide(false)
//        }

        checkUpdate()

        btnUnicode.setOnClickListener {
            var editor = sharedPreference.edit()
            editor.putString("language", "unicode")
            editor.commit()
            startActivity(Intent(this, MainActivity::class.java))
        }
        btnZawgyi.setOnClickListener {
            var editor = sharedPreference.edit()
            editor.putString("language", "zawgyi")
            editor.commit()
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    fun checkUpdate() {

        progress_circular.visibility = View.VISIBLE

        val call = api.checkUpdate("update")
        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {

                if (response.body().equals("n") && lang != "")
                    startActivity(Intent(this@StartActivity, MainActivity::class.java))
                else if (response.body().equals("y"))
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://play.google.com/store/apps/details?id=com.tencent.ig&hl=en")
                        )
                    )
                else viewHide(false)
                //loading!!.dismiss()
                progress_circular.visibility = View.INVISIBLE
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                progress_circular.visibility = View.INVISIBLE
                if (t is IOException) {
                    var str: String? = null
                    if (t is IOException)
                        str = "No Internet Connection!"
                    else str = "Something went wrong"

                    val dialogBuilder = AlertDialog.Builder(this@StartActivity)
                    dialogBuilder.setMessage(str).setCancelable(false)
                        .setPositiveButton("Retry", DialogInterface.OnClickListener { dialog, id ->
                            checkUpdate() //--recall chekupdate function to reconnect
                        }).create().show()
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
            linearLayout2.visibility = View.VISIBLE
            cc.visibility = View.VISIBLE
        }
    }

}
