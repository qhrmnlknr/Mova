package com.example.movamovieapp.utils

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.movamovieapp.utils.Constants.BASE_URL_IMAGE_YOUTUBE
import com.example.movamovieapp.utils.Constants.IMAGE_BASE_URL
import com.example.movamovieapp.utils.Constants.YOUTUBE_QUALITY

fun View.makeVisible(){
    this.visibility = View.VISIBLE
}

fun View.makeGone(){
    this.visibility = View.GONE
}

fun ImageView.loadURL(url : String){
    Glide.with(this.context)
        .load(IMAGE_BASE_URL + url)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(this)
}

