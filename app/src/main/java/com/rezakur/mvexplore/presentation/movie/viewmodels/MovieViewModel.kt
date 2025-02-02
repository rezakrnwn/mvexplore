package com.rezakur.mvexplore.presentation.movie.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rezakur.core.base.BaseViewModel
import com.rezakur.core.network.Resource
import com.rezakur.core.domain.usecases.GetNowPlayingMovies
import com.rezakur.core.domain.usecases.GetNowPlayingMoviesParams
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MovieViewModel(val getNowPlayingMovies: GetNowPlayingMovies) : ViewModel(),
    BaseViewModel<MovieState, MovieIntent> {
    private val _state = MutableStateFlow(MovieState.initial())
    override val state: StateFlow<MovieState> = _state

    override fun handleIntent(intent: MovieIntent) {
        when (intent) {
            is MovieIntent.LoadMovies -> {
                getMovies(intent.apiKey, intent.page)
            }
        }
    }

    private fun getMovies(apiKey: String, page: Int) {
        viewModelScope.launch {
            getNowPlayingMovies(
                GetNowPlayingMoviesParams(
                    apiKey = apiKey,
                    page = page
                )
            ).collectLatest { movieResult ->
                when (movieResult) {
                    is Resource.Loading -> {
                        _state.update { it.copy(status = MovieStatus.LOADING) }
                    }

                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                status = MovieStatus.LOADED,
                                movies = it.movies + (movieResult.data ?: emptyList()),
                                hasReachedMax = (movieResult.data?.size ?: 0) < 20
                            )
                        }
                    }

                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                status = MovieStatus.ERROR,
                                message = movieResult.message ?: "Unknown error"
                            )
                        }
                    }
                }
            }
        }
    }
}