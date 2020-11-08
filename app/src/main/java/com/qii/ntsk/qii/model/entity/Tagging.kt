package com.qii.ntsk.qii.model.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Tagging(
        @Json(name = "name")
        val name: String
)