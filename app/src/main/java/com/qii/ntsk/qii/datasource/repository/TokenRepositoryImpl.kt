package com.qii.ntsk.qii.datasource.repository

import com.qii.ntsk.qii.BuildConfig
import com.qii.ntsk.qii.model.entity.Token
import com.qii.ntsk.qii.datasource.service.QiitaService
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenRepositoryImpl @Inject constructor(private val service: QiitaService) : TokenRepository {
    override suspend fun getToken(code: String): Response<Token> {
        return service.getToken(
                code = code,
                clientId = BuildConfig.CLIENT_ID,
                clientSecret = BuildConfig.CLIENT_SECRET
        )
    }

    override suspend fun revokeToken(token: String): Response<Void> {
        return service.revokeToken(
                token = token
        )
    }
}