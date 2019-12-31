package com.qii.ntsk.qii.ui

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.qii.ntsk.qii.QiiApp
import com.qii.ntsk.qii.model.service.GlideApp

object ViewBindingAdapter {

    @BindingAdapter("imageUrl")
    @JvmStatic
    fun setImageUrl(view: ImageView, url: String?) {
        GlideApp.with(QiiApp.instance.applicationContext)
                .load(url)
                .override(view.width, view.height)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(view)
    }
}