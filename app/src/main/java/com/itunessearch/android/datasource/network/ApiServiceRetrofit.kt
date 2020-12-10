package com.itunessearch.android.datasource.network

import com.itunessearch.android.datasource.model.EntityNetworkSearchRes
import com.itunessearch.android.domain.model.Media
import com.squareup.moshi.Moshi
import retrofit2.HttpException
import retrofit2.http.GET
import retrofit2.http.Query
import java.io.IOException

interface ApiServiceRetrofit {

    @GET("search")
    suspend fun get(
        @Query("country") country: String,
        @Query("term") term: String?,
        @Query("media") media: Media
    ): EntityNetworkSearchRes
}

/**
 * Call Retrofit service which then wraps the response to specific type to allow
 * error response be handled specifically to its type.
 */
suspend fun <T> safeApiCall(apiCall: suspend () -> T): ResultWrapper<T> {
    return try {
        ResultWrapper.Success(apiCall.invoke())
    } catch (throwable: Throwable) {
        when (throwable) {
            is IOException -> ResultWrapper.NetworkError
            is HttpException -> {
                val code = throwable.code()
                val errorResponse = convertErrorBody(throwable)
                ResultWrapper.GenericError(code, errorResponse)
            }
            else -> {
                ResultWrapper.GenericError(null, null)
            }
        }
    }
}

private fun convertErrorBody(throwable: HttpException): ErrorResponse? {
    return try {
        throwable.response()?.errorBody()?.source()?.let {
            val moshiAdapter = Moshi.Builder()
                .build()
                .adapter(ErrorResponse::class.java)
            moshiAdapter.fromJson(it)
        }
    } catch (exception: Exception) {
        null
    }
}

data class ErrorResponse(
    val errors: List<String>
)

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T): ResultWrapper<T>()
    data class GenericError(val code: Int? = null, val error: ErrorResponse? = null): ResultWrapper<Nothing>()
    object NetworkError: ResultWrapper<Nothing>()
}
