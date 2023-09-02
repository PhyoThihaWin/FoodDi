package com.pthw.food.utils

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.pthw.food.R
import com.squareup.picasso.Picasso

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun View.toGone() {
    this.visibility = View.GONE
}

fun View.toVisible() {
    this.visibility = View.VISIBLE
}

fun View.toInVisible() {
    this.visibility = View.INVISIBLE
}

fun Context.inflater(): LayoutInflater {
    return LayoutInflater.from(this)
}

fun ImageView.loadIntoPicasso(task: Task<Uri>) {
    task.addOnSuccessListener {
        Picasso.get().load(it)
            .fit().centerInside().placeholder(R.drawable.logoblack)
            .into(this)
    }
}