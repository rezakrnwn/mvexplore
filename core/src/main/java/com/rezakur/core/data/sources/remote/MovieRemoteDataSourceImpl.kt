package com.rezakur.core.data.sources.remote

import com.rezakur.core.data.sources.remote.response.MovieDetailResponse
import com.rezakur.core.data.sources.remote.response.MovieResponse
import com.rezakur.core.network.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MovieRemoteDataSourceImpl(private val movieApi: MovieApi) : MovieRemoteDataSource {
    override suspend fun getNowPlaying(
        apiKey: String,
        page: Int,
    ): Flow<ApiResponse<List<MovieResponse>>> =
        flow {
            val response = movieApi.getNowPlaying(apiKey = apiKey, page = page)
            if (!response.results.isNullOrEmpty()) {
                emit(ApiResponse.Success(response.results))
            } else {
                emit(ApiResponse.Empty)
            }
        }.catch { e ->
            emit(ApiResponse.Error(errorMessage = e.message ?: "Unknown error"))
        }.flowOn(Dispatchers.IO)

    override suspend fun getMovieDetail(
        apiKey: String,
        id: Int,
    ): Flow<ApiResponse<MovieDetailResponse>> = flow<ApiResponse<MovieDetailResponse>> {
        val result = movieApi.getMovieDetail(apiKey = apiKey, id = id)
        emit(ApiResponse.Success(result))
    }.catch { e ->
        emit(ApiResponse.Error(errorMessage = e.message ?: "Unknown error"))
    }.flowOn(Dispatchers.IO)

}