package com.rezakur.mvexplore.domain.usecases

import com.rezakur.mvexplore.core.base.UseCase
import com.rezakur.mvexplore.domain.models.CatalogDetail
import com.rezakur.mvexplore.domain.repositories.FavoriteRepository

class AddFavorite(
    private val favoriteRepository: FavoriteRepository
) :
    UseCase<AddFavoriteParams, Long> {

    override suspend fun invoke(params: AddFavoriteParams): Long =
        favoriteRepository.insert(
            catalogTypeId = params.catalogTypeId,
            catalogDetail = params.catalogDetail
        )
}

data class AddFavoriteParams(val catalogTypeId: Int, val catalogDetail: CatalogDetail)