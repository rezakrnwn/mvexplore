package com.rezakur.mvexplore.presentation.detail.viewmodels

import com.rezakur.mvexplore.core.base.BaseViewIntent


sealed class DetailIntent : BaseViewIntent {
    data class LoadMovieDetail(val apiKey: String, val id: Int) : DetailIntent()
    data class LoadTvShowDetail(val apiKey: String, val id: Int) : DetailIntent()
    data class AddFavorite(val catalogTypeId: Int) : DetailIntent()
    object RemoveFavorite : DetailIntent()
}