package com.qii.ntsk.qii.model.entity

import java.io.Serializable

data class Tag(
        val id: String,
        val followersCount: Int,
        val itemsCount: Int,
        val iconUrl: String?
) : Serializable