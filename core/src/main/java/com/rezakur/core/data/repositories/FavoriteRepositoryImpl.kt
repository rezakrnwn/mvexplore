package com.rezakur.core.data.repositories

import com.rezakur.core.data.sources.local.FavoriteLocalDataSource
import com.rezakur.core.data.sources.local.entity.FavoriteEntity
import com.rezakur.core.domain.models.CatalogDetail
import com.rezakur.core.domain.models.Favorite
import com.rezakur.core.domain.repositories.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavoriteRepositoryImpl(private val favoriteLocalDataSource: FavoriteLocalDataSource) :
    FavoriteRepository {
    override fun getAll(): Flow<List<Favorite>> =
        favoriteLocalDataSource.getAll().map { favoriteEntities ->
            favoriteEntities.map { favoriteEntity ->
                FavoriteEntity.Mapper.toFavoriteDomain(favoriteEntity)
            }
        }

    override fun getById(catalogId: Int): Flow<Favorite?> =
        favoriteLocalDataSource.getById(id = catalogId).map { favoriteEntity ->
            favoriteEntity?.let {
                FavoriteEntity.Mapper.toFavoriteDomain(favoriteEntity)
            }
        }

    override suspend fun insert(catalogTypeId: Int, catalogDetail: CatalogDetail): Long =
        favoriteLocalDataSource.insert(
            favorite = FavoriteEntity.Mapper.fromCatalogDetailDomain(
                type = catalogTypeId,
                catalogDetail = catalogDetail
            )
        )

    override suspend fun delete(id: Int): Int = favoriteLocalDataSource.deleteById(id = id)
}