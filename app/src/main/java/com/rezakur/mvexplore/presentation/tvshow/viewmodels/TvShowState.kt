package com.rezakur.mvexplore.presentation.tvshow.viewmodels

import com.rezakur.mvexplore.core.base.BaseViewState
import com.rezakur.mvexplore.domain.models.Catalog

data class TvShowState(
    val status: TvShowStatus = TvShowStatus.INITIAL,
    val tvShows: List<Catalog> = emptyList(),
    val hasReachedMax: Boolean = false,
    val message: String = ""
) : BaseViewState {
    companion object {
        fun initial() = TvShowState(
            status = TvShowStatus.LOADING,
        )
    }
}

enum class TvShowStatus {
    INITIAL, LOADING, LOADED, ERROR
}