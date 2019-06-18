package com.pthw.food.adapter

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.developer.pthw.retrofittest.Api.ApiClient
import com.pthw.food.R
import com.pthw.food.model.Food

class ItemAdapter(var context: Context, var foodList: List<Food>) :
    RecyclerView.Adapter<ItemAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyViewHolder {
        val itemView = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.all_item_list, viewGroup, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(myViewHolder: MyViewHolder, i: Int) {
        val food = foodList.get(i)

        val sharedPreference = context.getSharedPreferences("myfile", Context.MODE_PRIVATE)
        val lang = sharedPreference.getString("language", "")

        when (lang) {
            "unicode" -> {
                myViewHolder.txtOne.setText(food.oneMM)
                myViewHolder.txtTwo.setText(food.twoMM)
                myViewHolder.txtDie.setText(food.dieMM)
            }
            "zawgyi" -> {
                myViewHolder.txtOne.setText(food.oneZ)
                myViewHolder.txtTwo.setText(food.twoZ)
                myViewHolder.txtDie.setText(food.dieZ)
            }
            else -> {
                myViewHolder.txtOne.setText(food.oneEN)
                myViewHolder.txtTwo.setText(food.twoEN)
                myViewHolder.txtDie.setText(food.dieEN)
            }
        }

        Glide.with(context)
            .load(ApiClient.BASE_URL + "/DiFood_photo/" + food.imgOne)
            .apply(
                RequestOptions()
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .dontAnimate()
                    .placeholder(R.drawable.ic_launcher_background)
            ).into(myViewHolder.img1)

        Glide.with(context)
            .load(ApiClient.BASE_URL + "/DiFood_photo/" + food.imgTwo)
            .apply(
                RequestOptions()
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .dontAnimate()
                    .placeholder(R.drawable.ic_launcher_background)
            ).into(myViewHolder.img2);

    }

    override fun getItemCount(): Int {
        return foodList.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    class MyViewHolder
        (itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txtOne: TextView
        var txtTwo: TextView
        var txtDie: TextView
        var img1: ImageView
        var img2: ImageView

        init {

            txtOne = itemView.findViewById(R.id.txtOne)
            txtTwo = itemView.findViewById(R.id.txtTwo)
            txtDie = itemView.findViewById(R.id.txtDie)
            img1 = itemView.findViewById(R.id.img1)
            img2 = itemView.findViewById(R.id.img2)

        }
    }
}
