package com.rezakur.core.domain.models

import com.rezakur.core.constant.CatalogType

data class Favorite(
    val id: Int,
    val type: CatalogType,
    val title: String,
    val voteAverage: Double,
    val imagePath: String,
    val overview: String,
)
