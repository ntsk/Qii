package com.qii.ntsk.qii.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import com.airbnb.epoxy.EpoxyRecyclerView
import com.qii.ntsk.qii.R

class PostsRecyclerView @JvmOverloads constructor(
        context: Context,
        attributeSet: AttributeSet? = null,
        defStyle: Int = 0
) : EpoxyRecyclerView(context, attributeSet, defStyle) {
    init {
        val dividerItemDecoration = DividerItemDecoration(context, VERTICAL)
        val drawable = ContextCompat.getDrawable(context, R.drawable.shape_inset_divider)
        if (drawable != null) {
            dividerItemDecoration.setDrawable(drawable)
        }
        addItemDecoration(dividerItemDecoration)
        layoutAnimation = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down)
    }
}