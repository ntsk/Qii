package com.qii.ntsk.qii.di.module

import com.qii.ntsk.qii.datasource.service.ApiClient
import com.qii.ntsk.qii.datasource.service.QiitaService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object ApplicationProvidesModule {

    @Provides
    fun provideService(): QiitaService {
        return ApiClient.create(QiitaService::class.java)
    }
}