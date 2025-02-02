package com.rezakur.mvexplore.data.sources.remote

import com.rezakur.mvexplore.core.network.ApiResponse
import com.rezakur.mvexplore.data.sources.remote.response.TvShowDetailResponse
import com.rezakur.mvexplore.data.sources.remote.response.TvShowsResponse
import kotlinx.coroutines.flow.Flow

interface TvShowRemoteDataSource {
    suspend fun getAiringToday(apiKey: String, page: Int): Flow<ApiResponse<List<TvShowsResponse>>>

    suspend fun getTvShowDetail(apiKey: String, id: Int): Flow<ApiResponse<TvShowDetailResponse>>
}