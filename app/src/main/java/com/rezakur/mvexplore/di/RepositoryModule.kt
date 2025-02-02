package com.rezakur.mvexplore.di

import com.rezakur.mvexplore.data.repositories.FavoriteRepositoryImpl
import com.rezakur.mvexplore.data.repositories.MovieRepositoryImpl
import com.rezakur.mvexplore.data.repositories.TvShowRepositoryImpl
import com.rezakur.mvexplore.data.sources.local.FavoriteLocalDataSource
import com.rezakur.mvexplore.data.sources.local.FavoriteLocalDataSourceImpl
import com.rezakur.mvexplore.data.sources.remote.MovieRemoteDataSource
import com.rezakur.mvexplore.data.sources.remote.MovieRemoteDataSourceImpl
import com.rezakur.mvexplore.data.sources.remote.TvShowRemoteDataSource
import com.rezakur.mvexplore.data.sources.remote.TvShowRemoteDataSourceImpl
import com.rezakur.mvexplore.domain.repositories.FavoriteRepository
import com.rezakur.mvexplore.domain.repositories.MovieRepository
import com.rezakur.mvexplore.domain.repositories.TvShowRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<MovieRemoteDataSource> {
        MovieRemoteDataSourceImpl(get())
    }
    single<TvShowRemoteDataSource> {
        TvShowRemoteDataSourceImpl(get())
    }
    single<FavoriteLocalDataSource> {
        FavoriteLocalDataSourceImpl(get())
    }
    single<MovieRepository> {
        MovieRepositoryImpl(get())
    }
    single<TvShowRepository> {
        TvShowRepositoryImpl(get())
    }
    single<FavoriteRepository> {
        FavoriteRepositoryImpl(get())
    }
}