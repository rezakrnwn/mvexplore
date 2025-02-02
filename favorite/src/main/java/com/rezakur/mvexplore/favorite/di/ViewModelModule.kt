package com.rezakur.mvexplore.favorite.di

import com.rezakur.mvexplore.favorite.viewmodels.FavoriteViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        FavoriteViewModel(
            getFavorites = get(),
        )
    }
}