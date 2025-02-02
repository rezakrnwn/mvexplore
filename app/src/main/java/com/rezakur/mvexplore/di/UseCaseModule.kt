package com.rezakur.mvexplore.di

import com.rezakur.mvexplore.domain.usecases.AddFavorite
import com.rezakur.mvexplore.domain.usecases.DeleteFavorite
import com.rezakur.mvexplore.domain.usecases.GetAiringTodayTvShow
import com.rezakur.mvexplore.domain.usecases.GetFavoriteById
import com.rezakur.mvexplore.domain.usecases.GetFavorites
import com.rezakur.mvexplore.domain.usecases.GetMovieDetail
import com.rezakur.mvexplore.domain.usecases.GetNowPlayingMovies
import com.rezakur.mvexplore.domain.usecases.GetTvShowDetail
import org.koin.dsl.module

val useCaseModule = module {
    factory { GetAiringTodayTvShow(get()) }
    factory { GetNowPlayingMovies(get()) }
    factory { GetMovieDetail(get()) }
    factory { GetTvShowDetail(get()) }
    factory { GetFavorites(get()) }
    factory { GetFavoriteById(get()) }
    factory { AddFavorite(get()) }
    factory { DeleteFavorite(get()) }
}