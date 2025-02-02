package com.rezakur.mvexplore.domain.usecases

import com.rezakur.mvexplore.core.base.UseCase
import com.rezakur.mvexplore.domain.models.Favorite
import com.rezakur.mvexplore.domain.repositories.FavoriteRepository
import kotlinx.coroutines.flow.Flow

class GetFavorites(
    private val favoriteRepository: FavoriteRepository
) :
    UseCase<UseCase.None, Flow<List<Favorite>>> {
    override suspend fun invoke(params: UseCase.None): Flow<List<Favorite>> =
        favoriteRepository.getAll()
}