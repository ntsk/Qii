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
import com.qii.ntsk.qii.model.entity.Tag
import com.qii.ntsk.qii.model.entity.Tagging
import kotlin.random.Random

class TagsChipView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {
    private val binding: ViewTagChipsBinding = ViewTagChipsBinding.inflate(LayoutInflater.from(context), this, true)
    private var tagsStateChangeListener: TagsStateChangeListener? = null

    fun setTags(tags: List<Tag>, closeIconVisibility: Boolean) {
        binding.viewTagChips.removeAllViews()
        tags.forEachIndexed { index, tag ->
            val chip: Chip = LayoutInflater.from(context).inflate(R.layout.view_tag_chip, binding.viewTagChips, false) as Chip
            binding.viewTagChips.addView(chip.also {
                it.isCloseIconVisible = closeIconVisibility
                it.setOnCloseIconClickListener {chip ->
                    tagsStateChangeListener?.onStateChanged(
                            tags.map { t ->
                                if(t.id == tag.id) {
                                    t.isSelected = false
                                }
                                t
                            }
                    )
                }
                it.id = View.generateViewId()
                it.text = tag.id
                it.chipIcon = generateColorDrawable(index)
            })
        }
    }

    fun setTaggings(tags: List<Tagging>, closeIconVisibility: Boolean) {
        binding.viewTagChips.removeAllViews()
        tags.forEachIndexed { index, tag ->
            val chip: Chip = LayoutInflater.from(context).inflate(R.layout.view_tag_chip, binding.viewTagChips, false) as Chip
            binding.viewTagChips.addView(chip.also {
                it.isCloseIconVisible = closeIconVisibility
                it.id = View.generateViewId()
                it.text = tag.name
                it.chipIcon = generateColorDrawable(index)
            })
        }
    }

    fun setTagsStateChangeListener(tagsStateChangeListener: TagsStateChangeListener) {
        this.tagsStateChangeListener = tagsStateChangeListener
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

    interface TagsStateChangeListener {
        fun onStateChanged(tags: List<Tag>)
    }
}