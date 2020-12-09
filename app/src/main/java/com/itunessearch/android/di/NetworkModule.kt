package com.itunessearch.android.di

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.itunessearch.android.BuildConfig
import com.itunessearch.android.datasource.network.ApiServiceRetrofit
import com.itunessearch.android.datasource.network.EnumConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun providesGsonBuilder(): Gson {
        return GsonBuilder().create()
    }

    @Singleton
    @Provides
    fun providesTimberTree(): Timber.Tree {
        class ReportingTree : Timber.Tree() {
            override fun log(
                priority: Int,
                tag: String?,
                message: String,
                throwable: Throwable?
            ) {
                if (priority == Log.VERBOSE || priority == Log.DEBUG) {
                    return
                }
                val t =
                    throwable ?: Exception(message)
                // Pass the exception variable t to crash reporting service
            }
        }

        return when(BuildConfig.DEBUG) {
            true -> Timber.DebugTree()
            else -> ReportingTree()
        }
    }

    @Singleton
    @Provides
    fun providesOkHttpClient(): OkHttpClient {
        val logger = object: HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Timber.tag("HTTP").d(message)
            }
        }

        val logging = HttpLoggingInterceptor(logger)
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build();

        return okHttpClient
    }

    @Singleton
    @Provides
    fun provideRetrofitBuilder(okHttpClient: OkHttpClient, gson: Gson): Retrofit.Builder {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BuildConfig.API_HOST)
            .addConverterFactory(EnumConverterFactory())
            .addConverterFactory(GsonConverterFactory.create(gson))
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit.Builder): ApiServiceRetrofit {
        return retrofit
            .build()
            .create(ApiServiceRetrofit::class.java)
    }
}
