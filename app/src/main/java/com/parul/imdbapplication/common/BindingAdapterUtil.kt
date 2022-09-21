package com.parul.imdbapplication.common

import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide


@BindingAdapter("weatherImage")
fun loadImage(view: ImageView, imageUrl: String?) {
    if (imageUrl.isNullOrBlank()) return
    Log.i("weather", "Image url- $imageUrl");
    Glide.with(view.context)
        .load(imageUrl)
        .into(view)
}

