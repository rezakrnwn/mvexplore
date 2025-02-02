package com.rezakur.mvexplore.core.base

interface BaseView<S : BaseViewState> {
    fun render(state: S)
}