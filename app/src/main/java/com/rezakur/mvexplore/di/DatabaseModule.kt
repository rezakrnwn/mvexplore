package com.rezakur.mvexplore.di

import androidx.room.Room
import com.rezakur.mvexplore.core.database.MVExploreDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            MVExploreDatabase::class.java, "MVExplore.db"
        ).fallbackToDestructiveMigration().build()
    }
    factory { get<MVExploreDatabase>().favoriteDao() }
}