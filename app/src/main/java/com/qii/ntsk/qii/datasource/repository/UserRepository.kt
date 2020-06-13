package com.qii.ntsk.qii.datasource.repository

import com.qii.ntsk.qii.model.entity.Post
import com.qii.ntsk.qii.model.entity.User
import com.qii.ntsk.qii.datasource.service.ApiClient
import com.qii.ntsk.qii.datasource.service.QiitaService
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor() {
    suspend fun getAuthenticatedUser(): Response<User> {
        return ApiClient.create(QiitaService::class.java).getAuthenticatedUser()
    }

    suspend fun getAuthenticatedUserItems(page: Int, perPage: Int): Response<List<Post>> {
        return ApiClient.create(QiitaService::class.java).getAuthenticatedUserItems(page.toString(), perPage.toString())
    }

    suspend fun getStocks(userId: String, page: Int, perPage: Int): Response<List<Post>> {
        return ApiClient.create(QiitaService::class.java).getUserStocks(userId, page.toString(), perPage.toString())
    }
}