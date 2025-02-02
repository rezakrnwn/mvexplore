package com.rezakur.core.base

sealed class ViewState<out T> {
    data class Success<T>(val data: T) : ViewState<T>()
    data class Error(val error: Throwable) : ViewState<Nothing>()
    object Loading : ViewState<Nothing>()
}
