package com.rezakur.core.domain.usecases

import com.rezakur.core.base.UseCase
import com.rezakur.core.domain.repositories.FavoriteRepository

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