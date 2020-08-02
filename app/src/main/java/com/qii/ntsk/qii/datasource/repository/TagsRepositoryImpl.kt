package com.qii.ntsk.qii.datasource.repository

import com.qii.ntsk.qii.datasource.service.QiitaService
import com.qii.ntsk.qii.model.entity.Tag
import retrofit2.Response
import javax.inject.Singleton

@Singleton
class TagsRepositoryImpl(private val service: QiitaService) : TagsRepository {
    override suspend fun fetch(page: String, per: String, sort: String): Response<List<Tag>> {
        return service.getTags(page, per, sort)
    }
}