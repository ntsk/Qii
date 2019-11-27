package com.qii.ntsk.qii.model.repository

import com.qii.ntsk.qii.model.service.ApiClient
import com.qii.ntsk.qii.model.service.QiitaService
import com.qii.ntsk.qii.model.entity.Post
import retrofit2.Response

class PostsRepository {
    suspend fun fetch(page: String, per: String, query: String?): Response<List<Post>> {
        return ApiClient.create(QiitaService::class.java)
                .getItems(page, per, query)
    }
}