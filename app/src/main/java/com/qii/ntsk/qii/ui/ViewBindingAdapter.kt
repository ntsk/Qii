package com.qii.ntsk.qii.ui

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.qii.ntsk.qii.QiiApp
import com.qii.ntsk.qii.datasource.service.GlideApp

object ViewBindingAdapter {

    @BindingAdapter("imageUrl")
    @JvmStatic
    fun setImageUrl(view: ImageView, url: String?) {
        GlideApp.with(QiiApp.instance.applicationContext)
                .load(url)
                .centerCrop()
                .into(view)
    }
}