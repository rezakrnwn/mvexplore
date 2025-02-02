package com.rezakur.core.domain.repositories

import com.rezakur.core.network.Resource
import com.rezakur.core.domain.models.Catalog
import com.rezakur.core.domain.models.CatalogDetail
import kotlinx.coroutines.flow.Flow

interface TvShowRepository {
    fun getAiringToday(apiKey: String, page: Int): Flow<Resource<List<Catalog>>>

    fun getTvShowDetail(apiKey: String, id: Int): Flow<Resource<CatalogDetail>>
}