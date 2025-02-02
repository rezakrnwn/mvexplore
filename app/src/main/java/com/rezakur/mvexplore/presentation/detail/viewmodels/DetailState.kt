package com.rezakur.mvexplore.presentation.detail.viewmodels

import com.rezakur.mvexplore.core.base.BaseViewState
import com.rezakur.mvexplore.core.network.Resource
import com.rezakur.mvexplore.domain.models.CatalogDetail

data class DetailState(
    val status: DetailStatus,
    val isFavorite: Boolean?,
    val catalogDetail: Resource<CatalogDetail>? = null
) : BaseViewState {
    companion object {
        fun initial(): DetailState = DetailState(
            status = DetailStatus.LOADING,
            isFavorite = false,
        )
    }
}

enum class DetailStatus {
    INITIAL, LOADING, LOADED, SUCCESS, ERROR
}