package com.qii.ntsk.qii.datasource.repository

import com.qii.ntsk.qii.BuildConfig
import com.qii.ntsk.qii.model.entity.Token
import com.qii.ntsk.qii.datasource.service.ApiClient
import com.qii.ntsk.qii.datasource.service.QiitaService
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenRepository @Inject constructor() {
    suspend fun getToken(code: String): Response<Token> {
        return ApiClient.create(QiitaService::class.java).getToken(
                code = code,
                clientId = BuildConfig.CLIENT_ID,
                clientSecret = BuildConfig.CLIENT_SECRET
        )
    }

    suspend fun revokeToken(token: String): Response<Void> {
        return ApiClient.create(QiitaService::class.java).revokeToken(
                token = token
        )
    }
}