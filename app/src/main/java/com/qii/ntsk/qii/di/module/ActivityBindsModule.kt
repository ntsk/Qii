package com.qii.ntsk.qii.di.module

import com.qii.ntsk.qii.datasource.repository.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class ActivityBindsModule {
    @Binds
    abstract fun bindPostsRepository(postsRepositoryImpl: PostsRepositoryImpl): PostsRepository

    @Binds
    abstract fun bindTagsRepository(tagsRepositoryImpl: TagsRepositoryImpl): TagsRepository

    @Binds
    abstract fun bindTokenRepository(tokensRepositoryImpl: TokenRepositoryImpl): TokenRepository

    @Binds
    abstract fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository
}