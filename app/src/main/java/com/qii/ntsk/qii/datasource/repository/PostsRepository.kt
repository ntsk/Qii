package com.qii.ntsk.qii.datasource.repository

import com.qii.ntsk.qii.model.entity.Post
import retrofit2.Response

interface PostsRepository {
    suspend fun fetch(page: String, per: String, query: String?): Response<List<Post>>
}