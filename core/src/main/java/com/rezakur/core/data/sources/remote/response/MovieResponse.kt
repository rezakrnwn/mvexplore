package com.rezakur.core.data.sources.remote.response

import com.google.gson.annotations.SerializedName
import com.rezakur.core.domain.models.Catalog

data class MovieResponse(
    @field:SerializedName("overview")
    val overview: String? = null,

    @field:SerializedName("original_language")
    val originalLanguage: String? = null,

    @field:SerializedName("original_title")
    val originalTitle: String? = null,

    @field:SerializedName("video")
    val video: Boolean? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("genre_ids")
    val genreIds: List<Int?>? = null,

    @field:SerializedName("poster_path")
    val posterPath: String? = null,

    @field:SerializedName("backdrop_path")
    val backdropPath: String? = null,

    @field:SerializedName("release_date")
    val releaseDate: String? = null,

    @field:SerializedName("popularity")
    val popularity: Double? = null,

    @field:SerializedName("vote_average")
    val voteAverage: Double? = null,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("adult")
    val adult: Boolean? = null,

    @field:SerializedName("vote_count")
    val voteCount: Int? = null
) {
    object Mapper {
        fun toCatalogDomain(response: MovieResponse) = Catalog(
            id = response.id,
            title = response.title ?: "",
            overview = response.overview ?: "",
            imagePath = response.posterPath ?: "",
            voteAverage = response.voteAverage ?: 0.0,
        )
    }
}