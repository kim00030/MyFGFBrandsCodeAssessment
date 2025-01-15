package com.dan.myfgfbrandscodeassessment.di

import android.content.Context
import com.dan.myfgfbrandscodeassessment.data.repository.DataStoreRepositoryImpl
import com.dan.myfgfbrandscodeassessment.data.repository.PostRepositoryImpl
import com.dan.myfgfbrandscodeassessment.domain.repository.DataStoreRepository
import com.dan.myfgfbrandscodeassessment.domain.repository.PostRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providePostRepository(): PostRepository = PostRepositoryImpl()

    @Singleton
    @Provides
    fun provideDataStoreRepository(@ApplicationContext context: Context): DataStoreRepository {
        return DataStoreRepositoryImpl(context)
    }

}