package com.qii.ntsk.qii.model.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class Post(
        val id: String?,
        val title: String?,
        val body: String?,

        @Json(name = "comment_count")
        val commentsCount: Int?,

        @Json(name = "likes_count")
        val likesCount: Int?,

        @Json(name = "page_views_count")
        val pageViewsCount: Int?,

        @Json(name = "created_at")
        val createdAt: String?,

        @Json(name = "updated_at")
        val updated_at: String?,

        val tags: List<Tag>?,
        val user: User?,
        val url: String?
): Serializable