package com.rezakur.core.di

import com.rezakur.core.domain.usecases.AddFavorite
import com.rezakur.core.domain.usecases.DeleteFavorite
import com.rezakur.core.domain.usecases.GetAiringTodayTvShow
import com.rezakur.core.domain.usecases.GetFavoriteById
import com.rezakur.core.domain.usecases.GetFavorites
import com.rezakur.core.domain.usecases.GetMovieDetail
import com.rezakur.core.domain.usecases.GetNowPlayingMovies
import com.rezakur.core.domain.usecases.GetTvShowDetail
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