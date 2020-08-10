package com.qii.ntsk.qii.datasource.repository

import com.qii.ntsk.qii.model.entity.Token
import retrofit2.Response

interface TokenRepository {
    suspend fun getToken(code: String): Response<Token>
    suspend fun revokeToken(token: String): Response<Void>
}