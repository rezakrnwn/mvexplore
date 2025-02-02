package com.rezakur.mvexplore.di

import com.rezakur.mvexplore.presentation.detail.viewmodels.DetailViewModel
import com.rezakur.mvexplore.presentation.favorite.viewmodels.FavoriteViewModel
import com.rezakur.mvexplore.presentation.movie.viewmodels.MovieViewModel
import com.rezakur.mvexplore.presentation.tvshow.viewmodels.TvShowViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        MovieViewModel(
            getNowPlayingMovies = get(),
        )
    }
    viewModel {
        TvShowViewModel(
            getAiringTodayTvShow = get(),
        )
    }
    viewModel {
        DetailViewModel(
            getMovieDetail = get(),
            getTvShowDetail = get(),
            getFavoriteById = get(),
            addFavorite = get(),
            deleteFavorite = get(),
        )
    }
    viewModel {
        FavoriteViewModel(
            getFavorites = get(),
        )
    }
}