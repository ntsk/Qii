package com.qii.ntsk.qii.model.entity

import com.qii.ntsk.qii.utils.DateFormatUtil
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class Post(
        @Json(name = "id")
        val id: String,

        @Json(name = "title")
        val title: String,

        @Json(name = "body")
        val body: String,

        @Json(name = "comments_count")
        val commentsCount: Int,

        @Json(name = "likes_count")
        val likesCount: Int,

        @Json(name = "page_views_count")
        val pageViewsCount: Int?,

        @Json(name = "created_at")
        val createdAt: String,

        @Json(name = "updated_at")
        val updatedAt: String,

        @Json(name = "tags")
        val tags: List<Tagging>,

        @Json(name = "user")
        val user: User,

        @Json(name = "url")
        val url: String
) : Serializable {
    fun tagsString(): String {
        return tags.joinToString(separator = ", ") { "#${it.name}" }
    }

    fun formattedCreatedAt(): String {
        return DateFormatUtil.formatTimeAndDate(createdAt)
    }
}