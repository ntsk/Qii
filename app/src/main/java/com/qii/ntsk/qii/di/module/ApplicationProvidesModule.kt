package com.qii.ntsk.qii.di.module

import com.qii.ntsk.qii.datasource.service.Connection
import com.qii.ntsk.qii.datasource.service.QiitaService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(ApplicationComponent::class)
object ApplicationProvidesModule {
    private const val BASE_URL = "https://qiita.com/api/v2/"

    @Provides
    fun provideService(): QiitaService {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(Connection.create())
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(QiitaService::class.java)
    }
}