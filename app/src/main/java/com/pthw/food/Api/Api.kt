package com.developer.pthw.retrofittest.Api

import com.pthw.food.model.Food
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Created by Belal on 10/2/2017.
 */

interface Api {

    @POST("DiFood_php/")
    @FormUrlEncoded
    fun getFoods(@Field("type") type: String): Call<List<Food>>

    @POST("DiFood_php/")
    @FormUrlEncoded
    fun getFilterItem(@Field("type") type: String, @Field("item") item: String): Call<List<Food>>

    @POST("DiFood_php/")
    @FormUrlEncoded
    fun getSearchItem(@Field("type") type: String, @Field("word") item: String): Call<List<Food>>

    @POST("DiFood_php/")
    @FormUrlEncoded
    fun checkUpdate(@Field("type") type: String): Call<String>


}

