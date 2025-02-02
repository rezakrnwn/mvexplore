package com.rezakur.mvexplore.presentation.detail.viewmodels

import com.rezakur.core.base.BaseViewState
import com.rezakur.core.network.Resource
import com.rezakur.core.domain.models.CatalogDetail

data class DetailState(
    val status: DetailStatus,
    val isFavorite: Boolean?,
    val catalogDetail: Resource<CatalogDetail>? = null
) : BaseViewState {
    companion object {
        fun initial(): DetailState = DetailState(
            status = DetailStatus.INITIAL,
            isFavorite = false,
        )
    }
}

enum class DetailStatus {
    INITIAL, LOADING, LOADED, ERROR
}