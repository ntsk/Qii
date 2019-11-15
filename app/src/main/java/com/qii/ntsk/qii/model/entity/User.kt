package com.qii.ntsk.qii.model.entity

data class User(
        val id: String,
        val permanentId: Int,
        val name: String?,
        val description: String?,
        val facebookId: String?,
        val linkedinId: String?,
        val twitterScreenName: String?,
        val followeesCount: Int,
        val followersCount: Int,
        val githubLoginName: String?,
        val location: String?,
        val organization: String?,
        val profileImageUrl: String,
        val websiteUrl: String?
)