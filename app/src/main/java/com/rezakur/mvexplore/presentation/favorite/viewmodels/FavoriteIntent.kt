package com.rezakur.mvexplore.presentation.favorite.viewmodels

import com.rezakur.mvexplore.core.base.BaseViewIntent

sealed class FavoriteIntent : BaseViewIntent {
    data object LoadFavorites : FavoriteIntent()
}