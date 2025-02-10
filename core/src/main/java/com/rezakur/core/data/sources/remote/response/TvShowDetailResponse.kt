package com.rezakur.core.data.sources.remote.response

import com.google.gson.annotations.SerializedName
import com.rezakur.core.domain.models.CatalogDetail
import com.rezakur.core.domain.models.ProductionCompany
import com.rezakur.core.domain.models.Season

data class TvShowDetailResponse(
    @SerializedName("adult")
    val adult: Boolean?,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("created_by")
    val createdBy: List<CreatedByTvShow?>?,
    @SerializedName("episode_run_time")
    val episodeRunTime: List<Int?>?,
    @SerializedName("first_air_date")
    val firstAirDate: String?,
    @SerializedName("genres")
    val genres: List<GenreTvShow?>?,
    @SerializedName("homepage")
    val homepage: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("in_production")
    val inProduction: Boolean?,
    @SerializedName("languages")
    val languages: List<String?>?,
    @SerializedName("last_air_date")
    val lastAirDate: String?,
    @SerializedName("last_episode_to_air")
    val lastEpisodeToAir: LastEpisodeToAirTvShow?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("networks")
    val networks: List<NetworkTvShow?>?,
    @SerializedName("next_episode_to_air")
    val nextEpisodeToAir: Any?,
    @SerializedName("number_of_episodes")
    val numberOfEpisodes: Int?,
    @SerializedName("number_of_seasons")
    val numberOfSeasons: Int?,
    @SerializedName("origin_country")
    val originCountry: List<String?>?,
    @SerializedName("original_language")
    val originalLanguage: String?,
    @SerializedName("original_name")
    val originalName: String?,
    @SerializedName("overview")
    val overview: String?,
    @SerializedName("popularity")
    val popularity: Double?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("production_companies")
    val productionCompanies: List<ProductionCompanyTvShow>?,
    @SerializedName("production_countries")
    val productionCountries: List<ProductionCountryTvShow>?,
    @SerializedName("seasons")
    val seasons: List<SeasonTvShow?>?,
    @SerializedName("spoken_languages")
    val spokenLanguages: List<SpokenLanguageTvShow>?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("tagline")
    val tagline: String?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("vote_average")
    val voteAverage: Double?,
    @SerializedName("vote_count")
    val voteCount: Int?
) {
    object Mapper {
        fun toCatalogDetailDomain(response: TvShowDetailResponse): CatalogDetail =
            CatalogDetail(
                id = response.id ?: 0,
                title = response.name ?: "",
                overview = response.overview ?: "",
                voteAverage = response.voteAverage ?: 0.0,
                imagePath = response.posterPath ?: "",
                releaseDate = response.firstAirDate ?: "",
                backdropPath = response.backdropPath ?: "",
                genres = response.genres?.joinToString { it?.name ?: "" } ?: "",
                seasons = response.seasons?.map { season ->
                    Season(
                        id = season?.id ?: 0,
                        title = season?.name ?: "",
                        posterPath = season?.posterPath ?: "",
                        voteAverage = season?.voteAverage ?: 0.0
                    )
                } ?: emptyList(),
                productionCompanies = response.productionCompanies?.map { productionCompany ->
                    ProductionCompany(
                        id = productionCompany.id ?: 0,
                        name = productionCompany.name ?: "",
                        posterPath = productionCompany.logoPath ?: "",
                    )
                } ?: emptyList(),
            )
    }
}

data class CreatedByTvShow(
    @SerializedName("credit_id")
    val creditId: String?,
    @SerializedName("gender")
    val gender: Int?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("profile_path")
    val profilePath: Any?
)

data class GenreTvShow(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?
)

data class LastEpisodeToAirTvShow(
    @SerializedName("air_date")
    val airDate: String?,
    @SerializedName("episode_number")
    val episodeNumber: Int?,
    @SerializedName("episode_type")
    val episodeType: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("overview")
    val overview: String?,
    @SerializedName("production_code")
    val productionCode: String?,
    @SerializedName("runtime")
    val runtime: Int?,
    @SerializedName("season_number")
    val seasonNumber: Int?,
    @SerializedName("show_id")
    val showId: Int?,
    @SerializedName("still_path")
    val stillPath: String?,
    @SerializedName("vote_average")
    val voteAverage: Double?,
    @SerializedName("vote_count")
    val voteCount: Int?
)

data class NetworkTvShow(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("logo_path")
    val logoPath: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("origin_country")
    val originCountry: String?
)

data class ProductionCompanyTvShow(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("logo_path")
    val logoPath: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("origin_country")
    val originCountry: String?
)

data class ProductionCountryTvShow(
    @SerializedName("iso_3166_1")
    val iso31661: String?,
    @SerializedName("name")
    val name: String?
)

data class SeasonTvShow(
    @SerializedName("air_date")
    val airDate: String?,
    @SerializedName("episode_count")
    val episodeCount: Int?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("overview")
    val overview: String?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("season_number")
    val seasonNumber: Int?,
    @SerializedName("vote_average")
    val voteAverage: Double?
)

data class SpokenLanguageTvShow(
    @SerializedName("english_name")
    val englishName: String?,
    @SerializedName("iso_639_1")
    val iso6391: String?,
    @SerializedName("name")
    val name: String?
)