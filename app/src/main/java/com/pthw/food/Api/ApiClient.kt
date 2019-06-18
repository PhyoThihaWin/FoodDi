package com.developer.pthw.retrofittest.Api

import com.google.gson.Gson
import com.google.gson.GsonBuilder

import java.util.concurrent.TimeUnit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {

    companion object {

        val BASE_URL = "https://minhtootintaung.com/phyothihawin/"
        var retrofit: Retrofit? = null

        //Here we are using the GsonConverterFactory to directly convert json data to object
        val client: Retrofit
            get() {

                if (retrofit == null) {

                    val okHttpClient = OkHttpClient.Builder()
                        .connectTimeout(1, TimeUnit.MINUTES)
                        .readTimeout(30, TimeUnit.SECONDS)
                        .writeTimeout(30, TimeUnit.SECONDS)
                        .build()

                    val gson = GsonBuilder()
                        .setLenient()
                        .create()

                    retrofit = Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .client(okHttpClient)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build()
                }
                return retrofit!!
            }

    }
}