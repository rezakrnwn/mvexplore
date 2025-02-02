package com.rezakur.mvexplore.data.sources.remote

import com.rezakur.mvexplore.data.sources.remote.response.MetaResponse
import com.rezakur.mvexplore.data.sources.remote.response.MovieResponse
import com.rezakur.mvexplore.data.sources.remote.response.TvShowDetailResponse
import com.rezakur.mvexplore.data.sources.remote.response.TvShowsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvShowApi {
    @GET("3/tv/airing_today")
    suspend fun getAiringToday(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int,
    ): MetaResponse<TvShowsResponse>

    @GET("3/tv/{id}")
    suspend fun getTvShowDetail(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String,
    ): TvShowDetailResponse
}