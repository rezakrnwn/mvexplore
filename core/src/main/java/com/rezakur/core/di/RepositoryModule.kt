package com.rezakur.core.di

import com.rezakur.core.data.repositories.FavoriteRepositoryImpl
import com.rezakur.core.data.repositories.MovieRepositoryImpl
import com.rezakur.core.data.repositories.TvShowRepositoryImpl
import com.rezakur.core.data.sources.local.FavoriteLocalDataSource
import com.rezakur.core.data.sources.local.FavoriteLocalDataSourceImpl
import com.rezakur.core.data.sources.remote.MovieRemoteDataSource
import com.rezakur.core.data.sources.remote.MovieRemoteDataSourceImpl
import com.rezakur.core.data.sources.remote.TvShowRemoteDataSource
import com.rezakur.core.data.sources.remote.TvShowRemoteDataSourceImpl
import com.rezakur.core.domain.repositories.FavoriteRepository
import com.rezakur.core.domain.repositories.MovieRepository
import com.rezakur.core.domain.repositories.TvShowRepository
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