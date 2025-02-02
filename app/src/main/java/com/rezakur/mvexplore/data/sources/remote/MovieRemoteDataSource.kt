package com.rezakur.mvexplore.data.sources.remote

import com.rezakur.mvexplore.core.network.ApiResponse
import com.rezakur.mvexplore.data.sources.remote.response.MovieDetailResponse
import com.rezakur.mvexplore.data.sources.remote.response.MovieResponse
import kotlinx.coroutines.flow.Flow

interface MovieRemoteDataSource {
    suspend fun getNowPlaying(apiKey: String, page: Int): Flow<ApiResponse<List<MovieResponse>>>

    suspend fun getMovieDetail(apiKey: String, id: Int): Flow<ApiResponse<MovieDetailResponse>>
}