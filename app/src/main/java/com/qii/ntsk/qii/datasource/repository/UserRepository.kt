package com.qii.ntsk.qii.datasource.repository

import com.qii.ntsk.qii.model.entity.Post
import com.qii.ntsk.qii.model.entity.User
import retrofit2.Response

interface UserRepository {
    suspend fun getAuthenticatedUser(): Response<User>
    suspend fun getAuthenticatedUserItems(page: Int, perPage: Int): Response<List<Post>>
    suspend fun getStocks(userId: String, page: Int, perPage: Int): Response<List<Post>>
}