package com.rezakur.core.domain.models

data class Catalog(
    val id: Int,
    val title: String?,
    val voteAverage: Double?,
    val imagePath: String?,
    val overview: String?,
)
