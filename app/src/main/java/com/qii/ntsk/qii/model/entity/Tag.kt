package com.qii.ntsk.qii.model.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class Tag(
        @Json(name = "id")
        val id: String,

        @Json(name = "followers_count")
        val followersCount: Int,

        @Json(name = "items_count")
        val itemsCount: Int,

        @Json(name = "icon_url")
        val iconUrl: String?,

        var isSelected: Boolean = false
) : Serializable