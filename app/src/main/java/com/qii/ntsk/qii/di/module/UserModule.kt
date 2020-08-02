package com.qii.ntsk.qii.di.module

import com.qii.ntsk.qii.datasource.repository.UserRepository
import com.qii.ntsk.qii.datasource.repository.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class UserModule {
    @Binds
    abstract fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository
}