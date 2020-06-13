package com.qii.ntsk.qii.model.repository

import com.qii.ntsk.qii.model.entity.Tag
import com.qii.ntsk.qii.model.service.ApiClient
import com.qii.ntsk.qii.model.service.QiitaService
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TagsRepository @Inject constructor() {
    suspend fun fetch(page: String, per: String, sort: String): Response<List<Tag>> {
        return ApiClient.create(QiitaService::class.java)
                .getTags(page, per, sort)
    }
}