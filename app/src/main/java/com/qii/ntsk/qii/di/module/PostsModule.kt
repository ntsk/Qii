package com.qii.ntsk.qii.di.module

import com.qii.ntsk.qii.datasource.repository.PostsRepository
import com.qii.ntsk.qii.datasource.repository.PostsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class PostsModule {
    @Binds
    abstract fun bindPostsRepository(postsRepositoryImpl: PostsRepositoryImpl): PostsRepository
}