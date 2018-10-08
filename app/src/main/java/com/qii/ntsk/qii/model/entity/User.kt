package com.qii.ntsk.qii.model.entity

data class User(
        var id: String,
        var permanentId: Int,
        var name: String?,
        var description: String?,
        var facebookId: String?,
        var linkedinId: String?,
        var twitterScreenName: String?,
        var followeesCount: Int,
        var followersCount: Int,
        var githubLoginName: String?,
        var location: String?,
        var organization: String?,
        var profileImageUrl: String,
        var websiteUrl: String?
)