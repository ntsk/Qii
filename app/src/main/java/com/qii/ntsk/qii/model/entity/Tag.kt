package com.qii.ntsk.qii.model.entity

import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class Tag(
        val id: String?,
        val followersCount: Int?,
        val itemsCount: Int?,
        val iconUrl: String?,
        var isSelected: Boolean = false
) : Serializable