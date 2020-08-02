package com.qii.ntsk.qii.datasource.repository

import com.qii.ntsk.qii.model.entity.Tag
import retrofit2.Response

interface TagsRepository {
    suspend fun fetch(page: String, per: String, sort: String): Response<List<Tag>>
}