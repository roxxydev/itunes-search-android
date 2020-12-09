package com.itunessearch.android.di

import android.content.Context
import com.itunessearch.android.datasource.cache.DaoContent
import com.itunessearch.android.datasource.mapper.ContentMapper
import com.itunessearch.android.datasource.network.ApiServiceRetrofit
import com.itunessearch.android.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideRepository(
        @ApplicationContext appContext: Context,
        daoContent: DaoContent,
        retrofit: ApiServiceRetrofit,
        contentEntityMapper: ContentMapper
    ): MainRepository {
        return MainRepository(
            appContext,
            daoContent,
            retrofit,
            contentEntityMapper
        )
    }
}
