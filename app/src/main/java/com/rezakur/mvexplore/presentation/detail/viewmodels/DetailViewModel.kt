package com.rezakur.mvexplore.presentation.detail.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rezakur.core.base.BaseViewModel
import com.rezakur.core.network.Resource
import com.rezakur.core.domain.usecases.AddFavorite
import com.rezakur.core.domain.usecases.AddFavoriteParams
import com.rezakur.core.domain.usecases.DeleteFavorite
import com.rezakur.core.domain.usecases.DeleteFavoriteParams
import com.rezakur.core.domain.usecases.GetFavoriteById
import com.rezakur.core.domain.usecases.GetFavoriteByIdParams
import com.rezakur.core.domain.usecases.GetMovieDetail
import com.rezakur.core.domain.usecases.GetMovieDetailParams
import com.rezakur.core.domain.usecases.GetTvShowDetail
import com.rezakur.core.domain.usecases.GetTvShowDetailParams
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailViewModel(
    private val getMovieDetail: GetMovieDetail,
    private val getTvShowDetail: GetTvShowDetail,
    private val getFavoriteById: GetFavoriteById,
    private val addFavorite: AddFavorite,
    private val deleteFavorite: DeleteFavorite,
) : ViewModel(), BaseViewModel<DetailState, DetailIntent> {
    private val _state = MutableStateFlow(DetailState.initial())
    override val state: StateFlow<DetailState> = _state

    override fun handleIntent(intent: DetailIntent) {
        viewModelScope.launch {
            when (intent) {
                is DetailIntent.LoadMovieDetail -> {
                    loadMovieDetail(apiKey = intent.apiKey, id = intent.id)
                }

                is DetailIntent.LoadTvShowDetail -> {
                    loadTvShowDetail(apiKey = intent.apiKey, id = intent.id)
                }

                is DetailIntent.AddFavorite -> {
                    addFavorite(intent.catalogTypeId)
                }

                is DetailIntent.RemoveFavorite -> {
                    removeFavorite()
                }
            }
        }
    }

    private suspend fun loadMovieDetail(apiKey: String, id: Int) {
        _state.update { it.copy(status = DetailStatus.LOADING) }
        getMovieDetail(
            GetMovieDetailParams(
                apiKey, id
            )
        ).collectLatest { movieDetailResult ->
            when (movieDetailResult) {
                is Resource.Success -> {
                    getFavoriteById(GetFavoriteByIdParams(id)).collectLatest { favorite ->
                        _state.update {
                            it.copy(
                                status = DetailStatus.LOADED,
                                catalogDetail = movieDetailResult,
                                isFavorite = favorite != null,
                            )
                        }
                    }
                }

                else -> {
                    _state.update {
                        it.copy(
                            status = DetailStatus.ERROR,
                        )
                    }
                }
            }
        }
    }

    private suspend fun loadTvShowDetail(apiKey: String, id: Int) {
        _state.update { it.copy(status = DetailStatus.LOADING) }
        getTvShowDetail(
            GetTvShowDetailParams(
                apiKey, id
            )
        ).collectLatest { tvShowDetailResult ->
            when (tvShowDetailResult) {
                is Resource.Success -> {
                    getFavoriteById(GetFavoriteByIdParams(id)).collectLatest { favorite ->
                        _state.update {
                            it.copy(
                                status = DetailStatus.LOADED,
                                catalogDetail = tvShowDetailResult,
                                isFavorite = favorite != null
                            )
                        }
                    }
                }

                else -> {
                    _state.update {
                        it.copy(
                            status = DetailStatus.ERROR,
                        )
                    }
                }
            }
        }
    }

    private suspend fun addFavorite(catalogTypeId: Int) {
        state.value.catalogDetail?.data?.let {
            addFavorite(
                AddFavoriteParams(
                    catalogTypeId = catalogTypeId, catalogDetail = it
                )
            ).let { result ->
                if (result > 0) {
                    _state.update { state ->
                        state.copy(isFavorite = true)
                    }
                }
            }
        }
    }

    private suspend fun removeFavorite() {
        state.value.catalogDetail?.data?.let {
            deleteFavorite(
                DeleteFavoriteParams(
                    id = it.id,
                )
            )
        }.let { result ->
            if ((result ?: 0) > 0) {
                _state.update { it.copy(isFavorite = false) }
            }
        }
    }
}