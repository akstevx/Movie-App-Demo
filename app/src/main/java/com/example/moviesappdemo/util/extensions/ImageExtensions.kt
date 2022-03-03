package com.example.moviesappdemo.util.extensions

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.ObjectKey

fun ImageView.loadImage(fullImageUrl: String, defaultImage: Int, view: Context) {
    val requestOption = RequestOptions()
        .signature(ObjectKey(System.currentTimeMillis().toString()))
        .placeholder(defaultImage).centerCrop()
    Glide.with(view)
        .load(fullImageUrl)
        .dontAnimate()
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .apply(requestOption)
        .error(defaultImage)
        .into(this)
    val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
    Glide.with(view).load(fullImageUrl).apply(requestOptions).error(defaultImage).into(this)
}