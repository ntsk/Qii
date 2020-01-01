package com.qii.ntsk.qii.model.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(
        val id: String?,

        @Json(name = "permanent_id")
        val permanentId: Int?,

        val name: String?,

        val description: String?,

        @Json(name = "facebook_id")
        val facebookId: String?,

        @Json(name = "linkedin_id")
        val linkedinId: String?,

        @Json(name = "twitter_screen_name")
        val twitterScreenName: String?,

        @Json(name = "followees_count")
        val followeesCount: Int?,

        @Json(name = "followers_count")
        val followersCount: Int?,

        @Json(name = "github_login_name")
        val githubLoginName: String?,

        val location: String?,

        val organization: String?,

        @Json(name = "profile_image_url")
        val profileImageUrl: String?,

        @Json(name = "web_site_url")
        val websiteUrl: String?
)