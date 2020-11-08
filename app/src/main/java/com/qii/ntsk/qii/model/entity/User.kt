package com.qii.ntsk.qii.model.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(
        @Json(name = "id")
        val id: String,

        @Json(name = "permanent_id")
        val permanentId: Int,

        @Json(name = "name")
        val name: String?,

        @Json(name = "description")
        val description: String?,

        @Json(name = "facebook_id")
        val facebookId: String?,

        @Json(name = "linkedin_id")
        val linkedinId: String?,

        @Json(name = "twitter_screen_name")
        val twitterScreenName: String?,

        @Json(name = "followees_count")
        val followeesCount: Int,

        @Json(name = "followers_count")
        val followersCount: Int,

        @Json(name = "github_login_name")
        val githubLoginName: String?,

        @Json(name = "location")
        val location: String?,

        @Json(name = "organization")
        val organization: String?,

        @Json(name = "profile_image_url")
        val profileImageUrl: String,

        @Json(name = "web_site_url")
        val websiteUrl: String?,

        @Json(name = "items_count")
        val itemsCount: Int
) {
    fun idWithAtSign(): String {
        return "@$id"
    }
}