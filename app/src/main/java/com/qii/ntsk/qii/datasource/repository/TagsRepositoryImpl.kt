package com.qii.ntsk.qii.datasource.repository

import com.qii.ntsk.qii.model.entity.Tag
import com.qii.ntsk.qii.datasource.service.ApiClient
import com.qii.ntsk.qii.datasource.service.QiitaService
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TagsRepositoryImpl @Inject constructor() : TagsRepository {
    override suspend fun fetch(page: String, per: String, sort: String): Response<List<Tag>> {
        return ApiClient.create(QiitaService::class.java)
                .getTags(page, per, sort)
    }
}