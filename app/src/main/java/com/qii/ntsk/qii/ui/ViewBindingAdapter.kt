package com.qii.ntsk.qii.ui

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.qii.ntsk.qii.QiiApp
import com.qii.ntsk.qii.datasource.service.GlideApp
import com.qii.ntsk.qii.model.entity.Tag
import com.qii.ntsk.qii.model.entity.Tagging
import com.qii.ntsk.qii.ui.widget.TagsChipView

object ViewBindingAdapter {

    @BindingAdapter("imageUrl")
    @JvmStatic
    fun setImageUrl(view: ImageView, url: String?) {
        GlideApp.with(QiiApp.instance.applicationContext)
                .load(url)
                .centerCrop()
                .into(view)
    }

    @BindingAdapter("taggings", "closeIconVisibility")
    @JvmStatic
    fun setTaggings(view: TagsChipView, tags: List<Tagging>, closeIconVisibility: Boolean = false) {
        view.setTaggings(tags, closeIconVisibility)
    }

    @BindingAdapter("tags", "closeIconVisibility")
    @JvmStatic
    fun setTags(view: TagsChipView, tags: List<Tag>, closeIconVisibility: Boolean = false) {
        view.setTags(tags, closeIconVisibility)
    }
}