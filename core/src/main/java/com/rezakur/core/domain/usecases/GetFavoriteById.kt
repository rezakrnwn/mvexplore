package com.rezakur.core.domain.usecases

import com.rezakur.core.base.UseCase
import com.rezakur.core.domain.models.Favorite
import com.rezakur.core.domain.repositories.FavoriteRepository
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