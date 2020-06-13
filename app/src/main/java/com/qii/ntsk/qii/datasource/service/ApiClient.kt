package com.qii.ntsk.qii.datasource.service

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object ApiClient {
    private const val BASE_URL = "https://qiita.com/api/v2/"

    private val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(Connection.create())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

    fun <T> create(service: Class<T>): T {
        return retrofit.create(service)
    }
}