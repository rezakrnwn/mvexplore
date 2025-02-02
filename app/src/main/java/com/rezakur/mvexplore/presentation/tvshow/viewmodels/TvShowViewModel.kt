package com.rezakur.mvexplore.presentation.tvshow.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rezakur.mvexplore.core.base.BaseViewModel
import com.rezakur.mvexplore.core.network.Resource
import com.rezakur.mvexplore.domain.usecases.GetAiringTodayTvShow
import com.rezakur.mvexplore.domain.usecases.GetAiringTodayTvShowParams
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TvShowViewModel(private val getAiringTodayTvShow: GetAiringTodayTvShow) : ViewModel(),
    BaseViewModel<TvShowState, TvShowIntent> {
    private val _state = MutableStateFlow(TvShowState.initial())
    override val state: StateFlow<TvShowState> = _state

    override fun handleIntent(intent: TvShowIntent) {
        when (intent) {
            is TvShowIntent.LoadTvShows -> {
                getTvShows(
                    apiKey = intent.apiKey, page = intent.page
                )
            }
        }
    }

    private fun getTvShows(apiKey: String, page: Int) {
        _state.update { it.copy(status = TvShowStatus.LOADING) }
        viewModelScope.launch {
            getAiringTodayTvShow(
                GetAiringTodayTvShowParams(
                    apiKey = apiKey,
                    page = page
                )
            ).collectLatest { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                status = TvShowStatus.LOADED,
                                tvShows = it.tvShows + (result.data ?: emptyList()),
                                hasReachedMax = (result.data?.size ?: 0) < 20
                            )
                        }
                    }

                    else -> {
                        _state.update {
                            it.copy(
                                status = TvShowStatus.ERROR,
                                message = "Error when load data"
                            )
                        }
                    }
                }
            }
        }
    }
}