package com.qii.ntsk.qii.utils

import android.content.Context
import android.graphics.Color

class ColorsUtil {
    companion object {
        fun random(context: Context): Int {
            val arrayId = context.resources.getIdentifier("material_color", "array", context.packageName)
            val materialColors = context.resources.obtainTypedArray(arrayId)
            val index = (Math.random() * materialColors.length()).toInt()
            val result = materialColors.getColor(index, Color.WHITE)
            materialColors.recycle()
            return result
        }
    }
}