package com.rezakur.mvexplore.presentation.movie.viewmodels

import com.rezakur.core.base.BaseViewIntent

sealed class MovieIntent : BaseViewIntent {
    data class LoadMovies(
        val apiKey: String, val page: Int
    ) : MovieIntent()
}