package com.dan.myfgfbrandscodeassessment.di

import com.dan.myfgfbrandscodeassessment.data.repository.PostRepositoryImpl
import com.dan.myfgfbrandscodeassessment.domain.repository.PostRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providePostRepository(): PostRepository = PostRepositoryImpl()

}