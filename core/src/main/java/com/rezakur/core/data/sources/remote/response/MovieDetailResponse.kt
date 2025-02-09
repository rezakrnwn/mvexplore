package com.rezakur.core.data.sources.remote.response

import com.google.gson.annotations.SerializedName

data class MovieDetailResponse(
    @SerializedName("adult")
    val adult: Boolean?,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("belongs_to_collection")
    val belongsToCollection: MovieBelongsToCollection?,
    @SerializedName("budget")
    val budget: Int?,
    @SerializedName("genres")
    val genres: List<GenreMovie?>?,
    @SerializedName("homepage")
    val homepage: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("imdb_id")
    val imdbId: String?,
    @SerializedName("original_language")
    val originalLanguage: String?,
    @SerializedName("original_title")
    val originalTitle: String?,
    @SerializedName("overview")
    val overview: String?,
    @SerializedName("popularity")
    val popularity: Double?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("production_companies")
    val productionCompanies: List<ProductionCompanyMovie?>?,
    @SerializedName("production_countries")
    val productionCountries: List<ProductionCountryMovie?>?,
    @SerializedName("release_date")
    val releaseDate: String?,
    @SerializedName("revenue")
    val revenue: Int?,
    @SerializedName("runtime")
    val runtime: Int?,
    @SerializedName("spoken_languages")
    val spokenLanguages: List<SpokenLanguageMovie?>?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("tagline")
    val tagline: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("video")
    val video: Boolean?,
    @SerializedName("vote_average")
    val voteAverage: Double?,
    @SerializedName("vote_count")
    val voteCount: Int?
) {
    object Mapper {
        fun toCatalogDetailDomain(response: MovieDetailResponse): com.rezakur.core.domain.models.CatalogDetail =
            com.rezakur.core.domain.models.CatalogDetail(
                id = response.id ?: 0,
                title = response.title ?: "",
                overview = response.overview ?: "",
                voteAverage = response.voteAverage ?: 0.0,
                imagePath = response.posterPath ?: "",
                releaseDate = response.releaseDate ?: "",
                backdropPath = response.backdropPath ?: "",
                genres = response.genres?.joinToString {
                    it?.name ?: ""
                } ?: "",
                seasons = emptyList(),
                productionCompanies = emptyList(),
            )
    }
}

data class GenreMovie(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?
)

data class MovieBelongsToCollection(
    @SerializedName("id")
    val id: Int,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("backdrop_path")
    val backdropPath: String,
)

data class ProductionCompanyMovie(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("logo_path")
    val logoPath: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("origin_country")
    val originCountry: String?
)

data class ProductionCountryMovie(
    @SerializedName("iso_3166_1")
    val iso31661: String?,
    @SerializedName("name")
    val name: String?
)

data class SpokenLanguageMovie(
    @SerializedName("english_name")
    val englishName: String?,
    @SerializedName("iso_639_1")
    val iso6391: String?,
    @SerializedName("name")
    val name: String?
)