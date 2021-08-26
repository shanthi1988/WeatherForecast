package com.countries.weather.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide


class ImageBindingAdapter {
    companion object{
        @JvmStatic
        @BindingAdapter("app:imageUrl")
        fun loadWeatherImage(view: ImageView, url: String?) {
            if (!url.isNullOrEmpty()) {
                Glide.with(view.context).load(url).into(view)
            }
        }
    }
}