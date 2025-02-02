package com.rezakur.core.data.sources.remote

import com.rezakur.core.network.ApiResponse
import com.rezakur.core.data.sources.remote.response.TvShowDetailResponse
import com.rezakur.core.data.sources.remote.response.TvShowsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class TvShowRemoteDataSourceImpl(private val tvShowApi: TvShowApi) : TvShowRemoteDataSource {
    override suspend fun getAiringToday(
        apiKey: String,
        page: Int
    ): Flow<ApiResponse<List<TvShowsResponse>>> = flow {
        val response = tvShowApi.getAiringToday(apiKey = apiKey, page = page)
        if (!response.results.isNullOrEmpty()) {
            emit(ApiResponse.Success(response.results))
        } else {
            emit(ApiResponse.Empty)
        }
    }.catch { e ->
        emit(ApiResponse.Error(errorMessage = e.message ?: "Unknown error"))
    }.flowOn(Dispatchers.IO)

    override suspend fun getTvShowDetail(
        apiKey: String,
        id: Int
    ): Flow<ApiResponse<TvShowDetailResponse>> = flow<ApiResponse<TvShowDetailResponse>> {
        val response = tvShowApi.getTvShowDetail(apiKey = apiKey, id = id)

        emit(ApiResponse.Success(response))
    }.catch { e ->
        emit(ApiResponse.Error(errorMessage = e.message ?: "Unknown error"))
    }.flowOn(Dispatchers.IO)
}