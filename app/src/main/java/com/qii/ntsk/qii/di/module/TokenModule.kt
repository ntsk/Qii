package com.qii.ntsk.qii.di.module

import com.qii.ntsk.qii.datasource.repository.TokenRepository
import com.qii.ntsk.qii.datasource.repository.TokenRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class TokenModule {
    @Binds
    abstract fun bindTokenRepository(tokensRepositoryImpl: TokenRepositoryImpl): TokenRepository
}