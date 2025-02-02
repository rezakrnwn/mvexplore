package com.rezakur.core.domain.usecases

import com.rezakur.core.base.UseCase
import com.rezakur.core.domain.models.Favorite
import com.rezakur.core.domain.repositories.FavoriteRepository
import kotlinx.coroutines.flow.Flow

class GetFavorites(
    private val favoriteRepository: FavoriteRepository
) :
    UseCase<UseCase.None, Flow<List<Favorite>>> {
    override suspend fun invoke(params: UseCase.None): Flow<List<Favorite>> =
        favoriteRepository.getAll()
}