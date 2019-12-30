package com.qii.ntsk.qii.model.service

import com.qii.ntsk.qii.model.TokenHolder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor

object Connection {
    private val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
            .addInterceptor(AuthInterceptor())
            .build()

    fun create(): OkHttpClient {
        return okHttpClient
    }

    class AuthInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val url = chain.request().url()
            val token = TokenHolder().load()
            val requestBuilder = chain.request().newBuilder().url(url)

            if (token != null) {
                requestBuilder.addHeader("Authorization", "Bearer $token")
            }

            return chain.proceed(requestBuilder.build())
        }
    }
}