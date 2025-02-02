package com.rezakur.core.data.sources.local

import com.rezakur.core.data.sources.local.entity.FavoriteEntity
import com.rezakur.core.data.sources.local.room.FavoriteDao
import kotlinx.coroutines.flow.Flow

class FavoriteLocalDataSourceImpl(private val favoriteDao: FavoriteDao) : FavoriteLocalDataSource {
    override fun getAll(): Flow<List<FavoriteEntity>> = favoriteDao.getAll()

    override fun getById(id: Int): Flow<FavoriteEntity?> = favoriteDao.getById(id = id)

    override suspend fun insert(favorite: FavoriteEntity): Long =
        favoriteDao.insert(favorite = favorite)

    override suspend fun deleteById(id: Int): Int = favoriteDao.deleteById(id = id)
}