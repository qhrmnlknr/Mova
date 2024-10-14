package com.example.movamovieapp.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.movamovieapp.R
import com.example.movamovieapp.datamodels.Genre
import com.example.movamovieapp.utils.Constants.BASE_URL_IMAGE_YOUTUBE
import com.example.movamovieapp.utils.Constants.IMAGE_BASE_URL
import com.example.movamovieapp.utils.Constants.YOUTUBE_QUALITY


object BindingAdapters{
    @BindingAdapter("imageUrl")
    @JvmStatic
    fun loadImage(imageView: ImageView, url: String?) {
        url?.let {
            Glide.with(imageView.context)
                .load(IMAGE_BASE_URL + it)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView)
        }
    }

    @BindingAdapter("imageUrlYT")
    @JvmStatic
    fun loadImageYT(imageView: ImageView, url: String?) {
        url?.let {
            Glide.with(imageView.context)
                .load(BASE_URL_IMAGE_YOUTUBE + url + YOUTUBE_QUALITY)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView)
        }
    }


}
