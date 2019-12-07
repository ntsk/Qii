package com.qii.ntsk.qii.model.service

import com.qii.ntsk.qii.model.entity.Post
import com.qii.ntsk.qii.model.entity.Tag
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface QiitaService {
    @GET("items")
    suspend fun getItems(
            @Query("page") page: String,
            @Query("per_page") per: String,
            @Query("query") query: String?
    ): Response<List<Post>>

    @GET("tags")
    suspend fun getTags(
            @Query("page") page: String,
            @Query("per_page") per: String,
            @Query("sort") sort: String
    ): Response<List<Tag>>
}