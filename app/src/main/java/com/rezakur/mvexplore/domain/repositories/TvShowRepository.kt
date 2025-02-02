package com.rezakur.mvexplore.domain.repositories

import com.rezakur.mvexplore.core.network.Resource
import com.rezakur.mvexplore.domain.models.Catalog
import com.rezakur.mvexplore.domain.models.CatalogDetail
import kotlinx.coroutines.flow.Flow

interface TvShowRepository {
    fun getAiringToday(apiKey: String, page: Int): Flow<Resource<List<Catalog>>>

    fun getTvShowDetail(apiKey: String, id: Int): Flow<Resource<CatalogDetail>>
}