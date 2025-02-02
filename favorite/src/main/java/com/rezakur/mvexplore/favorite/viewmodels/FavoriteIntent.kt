package com.rezakur.mvexplore.favorite.viewmodels

import com.rezakur.core.base.BaseViewIntent

sealed class FavoriteIntent : BaseViewIntent {
    data object LoadFavorites : FavoriteIntent()
}