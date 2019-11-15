package com.qii.ntsk.qii.model.entity

data class Post(
        val id: String,
        val title: String,
        val body: String,
        val commentsCount: Int,
        val likesCount: Int,
        val pageViewsCount: Int,
        val created_at: String,
        val updated_at: String,
        val tags: List<Tag>,
        val user: User
)