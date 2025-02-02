package com.rezakur.mvexplore.presentation.tvshow.viewmodels

import com.rezakur.core.base.BaseViewIntent


sealed class TvShowIntent : BaseViewIntent {
    data class LoadTvShows(val apiKey: String, val page: Int) : TvShowIntent()
}
