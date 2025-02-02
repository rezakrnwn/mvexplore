package com.rezakur.mvexplore.domain.usecases

import com.rezakur.mvexplore.core.base.UseCase
import com.rezakur.mvexplore.domain.models.Favorite
import com.rezakur.mvexplore.domain.repositories.FavoriteRepository
import kotlinx.coroutines.flow.Flow

class GetFavoriteById(
    private val favoriteRepository: FavoriteRepository
) :
    UseCase<GetFavoriteByIdParams, Flow<Favorite?>> {
    override suspend fun invoke(params: GetFavoriteByIdParams): Flow<Favorite?> =
        favoriteRepository.getById(catalogId = params.id)
}

data class GetFavoriteByIdParams(
    val id: Int
)