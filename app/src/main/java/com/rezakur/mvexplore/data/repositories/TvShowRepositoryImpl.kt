package com.rezakur.mvexplore.data.repositories

import com.rezakur.mvexplore.core.network.ApiResponse
import com.rezakur.mvexplore.core.network.Resource
import com.rezakur.mvexplore.data.sources.remote.TvShowRemoteDataSource
import com.rezakur.mvexplore.data.sources.remote.response.TvShowDetailResponse
import com.rezakur.mvexplore.data.sources.remote.response.TvShowsResponse
import com.rezakur.mvexplore.domain.models.Catalog
import com.rezakur.mvexplore.domain.models.CatalogDetail
import com.rezakur.mvexplore.domain.repositories.TvShowRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class TvShowRepositoryImpl(private val tvShowRemoteDataSource: TvShowRemoteDataSource) :
    TvShowRepository {
    override fun getAiringToday(apiKey: String, page: Int): Flow<Resource<List<Catalog>>> =
        flow {
            emit(Resource.Loading())
            val result = tvShowRemoteDataSource.getAiringToday(apiKey = apiKey, page = page)
            when (val response = result.first()) {
                is ApiResponse.Success -> {
                    emit(Resource.Success(response.data.map {
                        TvShowsResponse.Mapper.toCatalogDomain(it)
                    }))
                }

                is ApiResponse.Empty -> {
                    emit(Resource.Success(emptyList()))
                }

                is ApiResponse.Error -> {
                    emit(Resource.Error(response.errorMessage))
                }
            }
        }.catch {
            emit(Resource.Error(it.message ?: "Unknown error"))
        }

    override fun getTvShowDetail(apiKey: String, id: Int): Flow<Resource<CatalogDetail>> = flow {
        emit(Resource.Loading())
        val result = tvShowRemoteDataSource.getTvShowDetail(apiKey = apiKey, id = id)
        when (val response = result.first()) {
            is ApiResponse.Success -> {
                emit(Resource.Success(TvShowDetailResponse.Mapper.toCatalogDetailDomain(response.data)))
            }
            is ApiResponse.Empty -> {
                emit(Resource.Success())
            }
            is ApiResponse.Error -> {
                emit(Resource.Error(response.errorMessage))
            }
        }
    }.catch {
        emit(Resource.Error(it.message ?: "Unknown error"))
    }
}