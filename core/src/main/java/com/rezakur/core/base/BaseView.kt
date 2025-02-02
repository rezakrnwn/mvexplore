package com.rezakur.core.base

interface BaseView<S : BaseViewState> {
    fun render(state: S)
}