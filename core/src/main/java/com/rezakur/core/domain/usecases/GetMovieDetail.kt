package com.rezakur.core.domain.usecases

import com.rezakur.core.base.UseCase
import com.rezakur.core.network.Resource
import com.rezakur.core.domain.models.CatalogDetail
import com.rezakur.core.domain.repositories.MovieRepository
import kotlinx.coroutines.flow.Flow

class GetMovieDetail(private val movieRepository: MovieRepository) :
    UseCase<GetMovieDetailParams, Flow<Resource<CatalogDetail>>> {
    override suspend fun invoke(params: GetMovieDetailParams): Flow<Resource<CatalogDetail>> =
        movieRepository.getMovieDetail(apiKey = params.apiKey, id = params.id)
}

data class GetMovieDetailParams(
    val apiKey: String,
    val id: Int,
)