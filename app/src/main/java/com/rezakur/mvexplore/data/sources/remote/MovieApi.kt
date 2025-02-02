package com.rezakur.mvexplore.data.sources.remote

import com.rezakur.mvexplore.data.sources.remote.response.MetaResponse
import com.rezakur.mvexplore.data.sources.remote.response.MovieDetailResponse
import com.rezakur.mvexplore.data.sources.remote.response.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface MovieApi {
    @GET("3/movie/now_playing")
    suspend fun getNowPlaying(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int,
    ): MetaResponse<MovieResponse>

    @GET("3/movie/{id}")
    suspend fun getMovieDetail(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String,
    ): MovieDetailResponse
}