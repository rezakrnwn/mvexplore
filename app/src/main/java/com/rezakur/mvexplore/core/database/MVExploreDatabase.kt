package com.rezakur.mvexplore.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rezakur.mvexplore.data.sources.local.entity.FavoriteEntity
import com.rezakur.mvexplore.data.sources.local.room.FavoriteDao

@Database(
    entities = [FavoriteEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MVExploreDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}