package com.rezakur.mvexplore.domain.usecases

import com.rezakur.mvexplore.core.base.UseCase
import com.rezakur.mvexplore.core.network.Resource
import com.rezakur.mvexplore.domain.models.Catalog
import com.rezakur.mvexplore.domain.repositories.MovieRepository
import kotlinx.coroutines.flow.Flow

class GetNowPlayingMovies(private val movieRepository: MovieRepository) :
    UseCase<GetNowPlayingMoviesParams, Flow<Resource<List<Catalog>>>> {
    override suspend fun invoke(params: GetNowPlayingMoviesParams): Flow<Resource<List<Catalog>>> =
        movieRepository.getNowPlaying(apiKey = params.apiKey, page = params.page)
}

data class GetNowPlayingMoviesParams(
    val apiKey: String,
    val page: Int,
)