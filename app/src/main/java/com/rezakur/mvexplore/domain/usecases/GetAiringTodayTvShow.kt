package com.rezakur.mvexplore.domain.usecases

import com.rezakur.mvexplore.core.base.UseCase
import com.rezakur.mvexplore.core.network.Resource
import com.rezakur.mvexplore.domain.models.Catalog
import com.rezakur.mvexplore.domain.repositories.TvShowRepository
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