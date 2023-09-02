package com.pthw.food.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.pthw.food.R
import com.pthw.food.model.Food
import com.pthw.food.utils.Rabbit
import com.pthw.food.utils.loadIntoPicasso

class ItemAdapter(val foodList: List<Food>, var lang: String) :
    RecyclerView.Adapter<ItemAdapter.MyViewHolder>() {

    companion object {
        val storage = Firebase.storage
        val storageRef = storage.reference
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyViewHolder {
        val itemView = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.all_item_list, viewGroup, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(myViewHolder: MyViewHolder, i: Int) {
        val food = foodList[i]

        when (lang) {
            "unicode" -> {
                myViewHolder.txtOne.text = food.oneMM
                myViewHolder.txtTwo.text = food.twoMM
                myViewHolder.txtDie.text = food.dieMM
            }

            "zawgyi" -> {
                myViewHolder.txtOne.text = Rabbit.uni2zg(food.oneMM)
                myViewHolder.txtTwo.text = Rabbit.uni2zg(food.twoMM)
                myViewHolder.txtDie.text = Rabbit.uni2zg(food.dieMM)
            }

            else -> {
                myViewHolder.txtOne.text = food.oneEN
                myViewHolder.txtTwo.text = food.twoEN
                myViewHolder.txtDie.text = food.dieEN
            }
        }


        val foodImageOne = storageRef.child("fooddi_photo/${food.imgOne}")
        val foodImageTwo = storageRef.child("fooddi_photo/${food.imgTwo}")
        myViewHolder.img1.loadIntoPicasso(foodImageOne.downloadUrl)
        myViewHolder.img2.loadIntoPicasso(foodImageTwo.downloadUrl)

    }

    override fun getItemCount(): Int {
        return foodList.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    class MyViewHolder
        (itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txtOne: TextView = itemView.findViewById(R.id.txtOne)
        var txtTwo: TextView = itemView.findViewById(R.id.txtTwo)
        var txtDie: TextView = itemView.findViewById(R.id.txtDie)
        var img1: ImageView = itemView.findViewById(R.id.img1)
        var img2: ImageView = itemView.findViewById(R.id.img2)
    }


}
