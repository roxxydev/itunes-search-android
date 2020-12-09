package com.itunessearch.android.repository

import android.content.Context
import com.itunessearch.android.BuildConfig
import com.itunessearch.android.R
import com.itunessearch.android.datasource.cache.DaoContent
import com.itunessearch.android.datasource.mapper.ContentMapper
import com.itunessearch.android.datasource.model.EntityCacheContent
import com.itunessearch.android.datasource.network.ApiServiceRetrofit
import com.itunessearch.android.domain.model.Content
import com.itunessearch.android.domain.model.Media
import com.itunessearch.android.domain.state.DataState
import com.itunessearch.android.domain.state.DataState.*
import com.itunessearch.android.domain.state.MessageType
import com.itunessearch.android.domain.state.StateMessage
import com.itunessearch.android.presentation.MainDataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import java.lang.Exception

class  MainRepository
constructor(
    private val appContext: Context,
    private val daoContent: DaoContent,
    private val apiService: ApiServiceRetrofit,
    private val contentMapper: ContentMapper
) {
    suspend fun getContents(term: String, media: Media): Flow<DataState<MainDataState>> = flow {

        try {
            emit(LOADING(true))

            val networkData = apiService.get(BuildConfig.DEFAULT_COUNTRY_CODE, term, media)
            val contentsNetwork: List<Content> = contentMapper.mapFromNetworkEntitySearchRes(networkData)

            daoContent.upsert(contentMapper.mapToCacheEntityList(contentsNetwork) as List<EntityCacheContent>)

            val cacheData = daoContent.get()
            val contentsCache = contentMapper.mapFromCacheEntityList(cacheData)

            if (contentsNetwork.isEmpty()) {
                val stateMsg = StateMessage(appContext.getString(R.string.empty_results), MessageType.INFO)
                emit(SUCCESS(MainDataState(null, media, term, contentsNetwork, contentsCache), stateMsg))
            }
            else {
                emit(SUCCESS(MainDataState(null, media, term, contentsNetwork, contentsCache)))
            }

            emit(LOADING(false))
        } catch (e: Exception) {

            Timber.e(e)
            val errorTxt = appContext.getString(R.string.error_message)
            val stateMsg = StateMessage(errorTxt, MessageType.ERROR)
            emit(ERROR(stateMsg))
        }
    }
}
