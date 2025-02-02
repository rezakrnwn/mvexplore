package com.rezakur.mvexplore.presentation.favorite.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rezakur.core.base.BaseViewModel
import com.rezakur.core.base.UseCase
import com.rezakur.core.domain.usecases.GetFavorites
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FavoriteViewModel(private val getFavorites: GetFavorites) : ViewModel(),
    BaseViewModel<FavoriteState, FavoriteIntent> {
    private val _state = MutableStateFlow(FavoriteState.initial())
    override val state: StateFlow<FavoriteState> = _state

    override fun handleIntent(intent: FavoriteIntent) {
        when(intent) {
            is FavoriteIntent.LoadFavorites -> {
                loadFavorites()
            }
        }
    }

    private fun loadFavorites() {
        _state.update { it.copy(status = FavoriteStatus.LOADING) }
        viewModelScope.launch {
            getFavorites(UseCase.None()).collectLatest { favorites ->
                _state.update {
                    it.copy(
                        status = FavoriteStatus.LOADED,
                        favorites = favorites,
                    )
                }
            }
        }
    }
}