package com.qii.ntsk.qii.ui.search

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.google.android.material.chip.Chip
import com.qii.ntsk.qii.R
import com.qii.ntsk.qii.databinding.ViewTagsBinding
import com.qii.ntsk.qii.model.entity.Tag
import com.qii.ntsk.qii.utils.ColorsUtil

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class TagsView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {
    private var binding: ViewTagsBinding = ViewTagsBinding.inflate(LayoutInflater.from(context), this, true)

    @ModelProp
    fun setTags(tags: List<Tag>) {
        binding.viewTagsChipGroup.removeAllViews()
        tags.forEach { tag ->
            val chip: Chip = LayoutInflater.from(context).inflate(R.layout.view_tag_chip, binding.viewTagsChipGroup, false) as Chip

            val drawable = GradientDrawable()
            drawable.setColor(ColorsUtil.random(context))
            drawable.shape = GradientDrawable.OVAL

            chip.id = View.generateViewId()
            chip.text = tag.id
            chip.chipIcon = drawable
            binding.viewTagsChipGroup.addView(chip)
        }
    }
}