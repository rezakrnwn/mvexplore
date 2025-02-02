package com.rezakur.mvexplore.presentation.favorite.viewmodels

import com.rezakur.mvexplore.core.base.BaseViewState
import com.rezakur.mvexplore.domain.models.Favorite

enum class FavoriteStatus {
    INITIAL, LOADING, LOADED
}
data class FavoriteState(
    val status: FavoriteStatus = FavoriteStatus.INITIAL,
    val favorites: List<Favorite> = emptyList(),
    val message: String = "",
): BaseViewState {
    companion object {
        fun initial() = FavoriteState(
            status = FavoriteStatus.INITIAL,
            favorites = emptyList(),
        )
    }
}
