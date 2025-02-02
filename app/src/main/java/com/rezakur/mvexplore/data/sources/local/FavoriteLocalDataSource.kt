package com.rezakur.mvexplore.data.sources.local

import com.rezakur.mvexplore.data.sources.local.entity.FavoriteEntity
import kotlinx.coroutines.flow.Flow

interface FavoriteLocalDataSource {
    fun getAll(): Flow<List<FavoriteEntity>>

    fun getById(id: Int): Flow<FavoriteEntity?>

    suspend fun insert(favorite: FavoriteEntity): Long

    suspend fun deleteById(id: Int): Int
}