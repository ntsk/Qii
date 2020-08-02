package com.qii.ntsk.qii.di.module

import com.qii.ntsk.qii.datasource.repository.TagsRepository
import com.qii.ntsk.qii.datasource.repository.TagsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class TagsModule {
    @Binds
    abstract fun bindTagsRepository(tagsRepositoryImpl: TagsRepositoryImpl): TagsRepository
}