package com.rezakur.core.domain.repositories

import com.rezakur.core.network.Resource
import com.rezakur.core.domain.models.Catalog
import com.rezakur.core.domain.models.CatalogDetail
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getNowPlaying(apiKey: String, page: Int): Flow<Resource<List<Catalog>>>

    fun getMovieDetail(apiKey: String, id: Int): Flow<Resource<CatalogDetail>>
}