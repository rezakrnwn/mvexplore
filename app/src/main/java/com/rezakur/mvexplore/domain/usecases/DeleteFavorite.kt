package com.rezakur.mvexplore.domain.usecases

import com.rezakur.mvexplore.core.base.UseCase
import com.rezakur.mvexplore.domain.repositories.FavoriteRepository

class DeleteFavorite(
    private val favoriteRepository: FavoriteRepository
) :
    UseCase<DeleteFavoriteParams, Int> {

    override suspend fun invoke(params: DeleteFavoriteParams): Int =
        favoriteRepository.delete(id = params.id)
}

data class DeleteFavoriteParams(
    val id: Int
)