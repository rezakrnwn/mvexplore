package com.rezakur.mvexplore.core.base

import kotlinx.coroutines.flow.StateFlow

interface BaseViewModel<S : BaseViewState, I : BaseViewIntent> {
    val state: StateFlow<S>
    fun handleIntent(intent: I)
}