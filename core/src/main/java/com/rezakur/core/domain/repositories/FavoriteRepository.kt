package com.rezakur.core.domain.repositories

import com.rezakur.core.domain.models.CatalogDetail
import com.rezakur.core.domain.models.Favorite
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    fun getAll(): Flow<List<Favorite>>

    fun getById(catalogId: Int): Flow<Favorite?>

    suspend fun insert(catalogTypeId: Int, catalogDetail: CatalogDetail): Long

    suspend fun delete(id: Int): Int
}