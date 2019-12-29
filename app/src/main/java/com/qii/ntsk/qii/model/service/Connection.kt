package com.qii.ntsk.qii.model.service

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

object Connection {
    private val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
            .build()

    fun create(): OkHttpClient {
        return okHttpClient
    }
}