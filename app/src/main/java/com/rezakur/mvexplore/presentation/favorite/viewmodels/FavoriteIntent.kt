package com.rezakur.mvexplore.presentation.favorite.viewmodels

import com.rezakur.core.base.BaseViewIntent

sealed class FavoriteIntent : BaseViewIntent {
    data object LoadFavorites : FavoriteIntent()
}