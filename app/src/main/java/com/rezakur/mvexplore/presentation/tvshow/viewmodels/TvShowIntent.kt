package com.rezakur.mvexplore.presentation.tvshow.viewmodels

import com.rezakur.mvexplore.core.base.BaseViewIntent


sealed class TvShowIntent : BaseViewIntent {
    data class LoadTvShows(val apiKey: String, val page: Int) : TvShowIntent()
}
