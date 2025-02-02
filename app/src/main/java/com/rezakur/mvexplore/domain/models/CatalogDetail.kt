package com.rezakur.mvexplore.domain.models

data class CatalogDetail(
    val id: Int,
    val title: String,
    val voteAverage: Double,
    val imagePath: String,
    val backdropPath: String,
    val overview: String,
    val genres: String,
    val releaseDate: String,
    val seasons: List<Season> = emptyList(),
    val productionCompanies: List<ProductionCompany> = emptyList()
)
