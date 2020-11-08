package com.qii.ntsk.qii.ui.search

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.google.android.material.chip.Chip
import com.qii.ntsk.qii.R
import com.qii.ntsk.qii.databinding.ViewSearchTagsBinding
import com.qii.ntsk.qii.model.entity.Tag
import com.qii.ntsk.qii.model.state.SearchQueryStore

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class SearchTagsView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {
    private val binding: ViewSearchTagsBinding = ViewSearchTagsBinding.inflate(LayoutInflater.from(context), this, true)

    @ModelProp
    fun setTags(tags: List<Tag>) {
        binding.viewTagsChipGroup.removeAllViews()
        tags.forEachIndexed { index, tag ->
            val chip: Chip = LayoutInflater.from(context).inflate(R.layout.view_search_tag_chip, binding.viewTagsChipGroup, false) as Chip
            binding.viewTagsChipGroup.addView(chip.also {
                it.id = View.generateViewId()
                it.text = tag.id
                it.chipIcon = generateColorDrawable(index)
                it.setOnClickListener {
                    tag.isSelected = chip.isChecked
                    SearchQueryStore.updateTags(tag)
                }
                it.isChecked = tag.isSelected
            })
        }
    }

    private fun generateColorDrawable(index: Int): GradientDrawable {
        val drawable = GradientDrawable()
        val arrayId = context.resources.getIdentifier("material_color", "array", context.packageName)
        val materialColors = context.resources.obtainTypedArray(arrayId)
        drawable.setColor(materialColors.getColor(index, Color.WHITE))
        materialColors.recycle()
        drawable.shape = GradientDrawable.OVAL
        return drawable
    }
}