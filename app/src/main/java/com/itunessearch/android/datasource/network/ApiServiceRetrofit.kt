package com.itunessearch.android.datasource.network

import com.itunessearch.android.BuildConfig
import com.itunessearch.android.datasource.model.EntityNetworkSearchRes
import com.itunessearch.android.domain.model.Media
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServiceRetrofit {

    @GET("search")
    suspend fun get(
        @Query("country") country: String,
        @Query("term") term: String?,
        @Query("media") media: Media
    ): EntityNetworkSearchRes
}
