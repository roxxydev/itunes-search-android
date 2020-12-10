package com.itunessearch.android.repository

import android.content.Context
import com.itunessearch.android.BuildConfig
import com.itunessearch.android.R
import com.itunessearch.android.datasource.cache.DaoContent
import com.itunessearch.android.datasource.mapper.ContentMapper
import com.itunessearch.android.datasource.model.EntityCacheContent
import com.itunessearch.android.datasource.model.EntityNetworkSearchRes
import com.itunessearch.android.datasource.network.ApiServiceRetrofit
import com.itunessearch.android.datasource.network.ResultWrapper
import com.itunessearch.android.datasource.network.safeApiCall
import com.itunessearch.android.domain.model.Content
import com.itunessearch.android.domain.model.Media
import com.itunessearch.android.domain.state.DataState
import com.itunessearch.android.domain.state.DataState.*
import com.itunessearch.android.domain.state.MessageType
import com.itunessearch.android.domain.state.StateMessage
import com.itunessearch.android.presentation.detail.DetailDataState
import com.itunessearch.android.presentation.main.MainDataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber

class  MainRepository
constructor(
    private val appContext: Context,
    private val daoContent: DaoContent,
    private val apiService: ApiServiceRetrofit,
    private val contentMapper: ContentMapper
) {

    /**
     * Return the list of Contents from ITunes Search API. Returned response from API will
     * be saved to Room database and serves as cache data.
     *
     * @param term The text to search in ITunes Search API.
     * @param media The type of Media to fetch.
     */
    suspend fun getContents(isInitial: Boolean, term: String, media: Media): Flow<DataState<MainDataState>> = flow {
        try {
            emit(LOADING(true))

            val response = safeApiCall { 
                apiService.get(BuildConfig.DEFAULT_COUNTRY_CODE, term, media)
            }

            when(response) {
                is ResultWrapper.Success<EntityNetworkSearchRes> -> {

                    val networkData = response.value
                    var contentsNetwork: List<Content> = networkData.let {
                        contentMapper.mapFromNetworkEntitySearchRes(it)
                    }

                    val entityCacheContentsList: List<EntityCacheContent>? =
                        contentMapper.mapToCacheEntityList(contentsNetwork) as List<EntityCacheContent>
                    entityCacheContentsList?.let {
                        daoContent.upsert(it)
                    }

                    val cacheData = daoContent.getAll()
                    val contentsCache = contentMapper.mapFromCacheEntityList(cacheData)

                    emit(SUCCESS(
                        MainDataState(
                            isInitial,
                            contentsNetwork,
                            contentsCache)
                    ))
                }
                else -> {
                    // Emit error with message if network issue or error response
                    val errorTxt = appContext.getString(R.string.error_network)
                    val stateMsg = StateMessage(errorTxt, MessageType.ERROR)

                    val cacheData = daoContent.getAll()
                    val contentsCache = contentMapper.mapFromCacheEntityList(cacheData)

                    emit(ERROR(stateMsg,
                        MainDataState(
                            isInitial,
                            emptyList(),
                            contentsCache)
                        )
                    )
                }
            }

            emit(LOADING(false))

        } catch (e: Exception) {
            Timber.e(e)
            val errorTxt = appContext.getString(R.string.error_message)
            val stateMsg = StateMessage(errorTxt, MessageType.ERROR)
            emit(ERROR(stateMsg))
        }
    }

    /**
     * Return the details of a Content from cache data.
     */
    suspend fun getContent(id: String): Flow<DataState<DetailDataState>> = flow {
        try {
            emit(LOADING(true))
            val cacheData = daoContent.get()
            val content = contentMapper.mapFromCacheEntity(cacheData)
            emit(SUCCESS(DetailDataState(content)))
            emit(LOADING(false))

        } catch (e: Exception) {
            Timber.e(e)
            val errorTxt = appContext.getString(R.string.error_message)
            val stateMsg = StateMessage(errorTxt, MessageType.ERROR)
            emit(ERROR(stateMsg))
        }
    }
}
