package com.rezakur.mvexplore.domain.usecases

import com.rezakur.mvexplore.core.base.UseCase
import com.rezakur.mvexplore.core.network.Resource
import com.rezakur.mvexplore.domain.models.CatalogDetail
import com.rezakur.mvexplore.domain.repositories.TvShowRepository
import kotlinx.coroutines.flow.Flow

class GetTvShowDetail(private val tvShowRepository: TvShowRepository) :
    UseCase<GetTvShowDetailParams, Flow<Resource<CatalogDetail>>> {
    override suspend fun invoke(params: GetTvShowDetailParams): Flow<Resource<CatalogDetail>> =
        tvShowRepository.getTvShowDetail(apiKey = params.apiKey, id = params.id)
}

data class GetTvShowDetailParams(
    val apiKey: String,
    val id: Int,
)