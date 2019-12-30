package com.qii.ntsk.qii.model.entity

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Token(
        val token: String
)