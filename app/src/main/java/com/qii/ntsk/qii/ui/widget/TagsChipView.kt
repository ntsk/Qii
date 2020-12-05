package com.qii.ntsk.qii.ui.widget

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.google.android.material.chip.Chip
import com.qii.ntsk.qii.R
import com.qii.ntsk.qii.databinding.ViewTagChipsBinding
import kotlin.random.Random

class TagsChipView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {
    private val binding: ViewTagChipsBinding = ViewTagChipsBinding.inflate(LayoutInflater.from(context), this, true)

    fun setTags(tags: List<String>) {
        binding.viewTagChips.removeAllViews()
        tags.forEachIndexed { index, tag ->
            val chip: Chip = LayoutInflater.from(context).inflate(R.layout.view_tag_chip, binding.viewTagChips, false) as Chip
            binding.viewTagChips.addView(chip.also {
                it.id = View.generateViewId()
                it.text = tag
                it.chipIcon = generateColorDrawable(index)
            })
        }
    }

    private fun generateColorDrawable(index: Int): GradientDrawable {
        val drawable = GradientDrawable()
        val arrayId = context.resources.getIdentifier("material_color", "array", context.packageName)
        val materialColors = context.resources.obtainTypedArray(arrayId)
        val random = Random.nextInt(36)
        drawable.setColor(materialColors.getColor(random, Color.WHITE))
        materialColors.recycle()
        drawable.shape = GradientDrawable.OVAL
        return drawable
    }
}