package com.pthw.food.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pthw.food.R
import com.pthw.food.model.Food
import com.pthw.food.utils.ConstantValue
import com.pthw.food.utils.Rabbit
import com.squareup.picasso.Picasso

class ItemAdapter(val context: Activity, val foodList: List<Food>, var lang: String) :
    RecyclerView.Adapter<ItemAdapter.MyViewHolder>() {

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



        Picasso.get().load(ConstantValue.path + food.imgOne)
            .fit().centerInside().placeholder(R.drawable.logoblack)
            .into(myViewHolder.img1)


        Picasso.get().load(ConstantValue.path + food.imgTwo)
            .fit().centerInside().placeholder(R.drawable.logoblack)
            .into(myViewHolder.img2)

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
