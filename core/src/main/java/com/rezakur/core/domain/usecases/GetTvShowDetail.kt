package com.rezakur.core.domain.usecases

import com.rezakur.core.base.UseCase
import com.rezakur.core.network.Resource
import com.rezakur.core.domain.models.CatalogDetail
import com.rezakur.core.domain.repositories.TvShowRepository
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