package com.qii.ntsk.qii.datasource.repository

import com.qii.ntsk.qii.datasource.service.ApiClient
import com.qii.ntsk.qii.datasource.service.QiitaService
import com.qii.ntsk.qii.model.entity.Post
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostsRepository @Inject constructor() {
    suspend fun fetch(page: String, per: String, query: String?): Response<List<Post>> {
        return ApiClient.create(QiitaService::class.java)
                .getItems(page, per, query)
    }
}