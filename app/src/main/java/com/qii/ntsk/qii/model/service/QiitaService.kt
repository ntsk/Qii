package com.qii.ntsk.qii.model.service

import com.qii.ntsk.qii.model.entity.Post
import com.qii.ntsk.qii.model.entity.Tag
import com.qii.ntsk.qii.model.entity.Token
import com.qii.ntsk.qii.model.entity.User
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface QiitaService {
    @GET("items")
    suspend fun getItems(
            @Query("page") page: String,
            @Query("per_page") per: String,
            @Query(value = "query", encoded = true) query: String?
    ): Response<List<Post>>

    @GET("tags")
    suspend fun getTags(
            @Query("page") page: String,
            @Query("per_page") per: String,
            @Query("sort") sort: String
    ): Response<List<Tag>>

    @POST("access_tokens")
    suspend fun getToken(
            @Query("client_id") clientId: String,
            @Query("client_secret") clientSecret: String,
            @Query("code") code: String
    ): Response<Token>

    @DELETE("access_token/{token}")
    suspend fun revokeToken(
            @Path("token") token: String
    ): Response<Void>

    @GET("authenticated_user")
    suspend fun getAuthenticatedUser(): Response<User>

    @GET("authenticated_user/items")
    suspend fun getAuthenticatedUserItems(): Response<List<Post>>
}