package com.qii.ntsk.qii.model.repository

import com.qii.ntsk.qii.model.entity.User
import com.qii.ntsk.qii.model.service.ApiClient
import com.qii.ntsk.qii.model.service.QiitaService
import retrofit2.Response

class UserRepository {
    suspend fun getAuthenticatedUser(): Response<User> {
        return ApiClient.create(QiitaService::class.java).getAuthenticatedUser()
    }
}