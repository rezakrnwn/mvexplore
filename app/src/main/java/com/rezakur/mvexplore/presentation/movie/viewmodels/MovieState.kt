package com.rezakur.mvexplore.presentation.movie.viewmodels

import com.rezakur.core.base.BaseViewState
import com.rezakur.core.domain.models.Catalog

enum class MovieStatus {
    INITIAL, LOADING, LOADED, ERROR
}
data class MovieState(
    val status: MovieStatus = MovieStatus.INITIAL,
    val movies: List<Catalog> = emptyList(),
    val hasReachedMax: Boolean = false,
    val message: String = ""
) : BaseViewState {
    companion object {
        fun initial() = MovieState(
            status = MovieStatus.INITIAL,
            movies = emptyList()
        )
    }
}