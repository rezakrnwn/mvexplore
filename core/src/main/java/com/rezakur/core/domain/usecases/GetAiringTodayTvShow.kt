package com.rezakur.core.domain.usecases

import com.rezakur.core.base.UseCase
import com.rezakur.core.network.Resource
import com.rezakur.core.domain.models.Catalog
import com.rezakur.core.domain.repositories.TvShowRepository
import kotlinx.coroutines.flow.Flow

class GetAiringTodayTvShow(private val tvShowRepository: TvShowRepository) :
    UseCase<GetAiringTodayTvShowParams, Flow<Resource<List<Catalog>>>> {
    override suspend fun invoke(params: GetAiringTodayTvShowParams): Flow<Resource<List<Catalog>>> =
        tvShowRepository.getAiringToday(apiKey = params.apiKey, page = params.page)
}

data class GetAiringTodayTvShowParams(
    val apiKey: String,
    val page: Int,
)