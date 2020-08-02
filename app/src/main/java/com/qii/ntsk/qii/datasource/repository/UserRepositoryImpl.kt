package com.qii.ntsk.qii.datasource.repository

import com.qii.ntsk.qii.datasource.service.QiitaService
import com.qii.ntsk.qii.model.entity.Post
import com.qii.ntsk.qii.model.entity.User
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(private val service: QiitaService) : UserRepository {
    override suspend fun getAuthenticatedUser(): Response<User> {
        return service.getAuthenticatedUser()
    }

    override suspend fun getAuthenticatedUserItems(page: Int, perPage: Int): Response<List<Post>> {
        return service.getAuthenticatedUserItems(page.toString(), perPage.toString())
    }

    override suspend fun getStocks(userId: String, page: Int, perPage: Int): Response<List<Post>> {
        return service.getUserStocks(userId, page.toString(), perPage.toString())
    }
}