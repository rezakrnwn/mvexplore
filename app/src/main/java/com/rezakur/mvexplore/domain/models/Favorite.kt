package com.rezakur.mvexplore.domain.models

import com.rezakur.mvexplore.core.constant.CatalogType

data class Favorite(
    val id: Int,
    val type: CatalogType,
    val title: String,
    val voteAverage: Double,
    val imagePath: String,
    val overview: String,
)
