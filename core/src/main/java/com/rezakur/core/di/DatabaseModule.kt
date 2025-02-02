package com.rezakur.core.di

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            com.rezakur.core.database.MVExploreDatabase::class.java, "MVExplore.db"
        ).fallbackToDestructiveMigration().build()
    }
    factory { get<com.rezakur.core.database.MVExploreDatabase>().favoriteDao() }
}