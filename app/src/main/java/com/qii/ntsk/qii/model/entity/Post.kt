package com.qii.ntsk.qii.model.entity

data class Post(
        var id: String,
        var title: String,
        var body: String,
        var commentsCount: Int,
        var likesCount: Int,
        var pageViewsCount: Int,
        var created_at: String,
        var updated_at: String,
        var tags: List<Tag>,
        var user : User
)