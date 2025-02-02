package com.rezakur.core.data.sources.remote.response

import com.google.gson.annotations.SerializedName
import com.rezakur.core.domain.models.Catalog

data class TvShowsResponse(
    @field:SerializedName("first_air_date")
    val firstAirDate: String? = null,

    @field:SerializedName("overview")
    val overview: String? = null,

    @field:SerializedName("original_language")
    val originalLanguage: String? = null,

    @field:SerializedName("genre_ids")
    val genreIds: List<Int?>? = null,

    @field:SerializedName("poster_path")
    val posterPath: String? = null,

    @field:SerializedName("origin_country")
    val originCountry: List<String?>? = null,

    @field:SerializedName("backdrop_path")
    val backdropPath: String? = null,

    @field:SerializedName("original_name")
    val originalName: String? = null,

    @field:SerializedName("popularity")
    val popularity: Double? = null,

    @field:SerializedName("vote_average")
    val voteAverage: Double? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("vote_count")
    val voteCount: Int? = null
) {
     object Mapper {
         fun toCatalogDomain(response: TvShowsResponse) = com.rezakur.core.domain.models.Catalog(
             id = response.id,
             title = response.name ?: "",
             overview = response.overview ?: "",
             voteAverage = response.voteAverage ?: 0.0,
             imagePath = response.posterPath ?: "",
         )
     }
}
