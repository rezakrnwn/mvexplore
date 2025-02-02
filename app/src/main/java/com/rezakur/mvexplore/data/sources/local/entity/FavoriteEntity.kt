package com.rezakur.mvexplore.data.sources.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rezakur.mvexplore.core.constant.CatalogType
import com.rezakur.mvexplore.domain.models.CatalogDetail
import com.rezakur.mvexplore.domain.models.Favorite

@Entity(tableName = "favorite")
data class FavoriteEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "type")
    val type: Int,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "overview")
    val overview: String?,
    @ColumnInfo(name = "poster_path")
    val posterPath: String?,
    @ColumnInfo(name = "release_date")
    val releaseDate: String?,
    @ColumnInfo(name = "genres")
    val genres: String?,
    @ColumnInfo(name = "vote_average")
    val voteAverage: Double = 0.0,
) {
    object Mapper {
        fun toFavoriteDomain(favoriteEntity: FavoriteEntity) = Favorite(
            id = favoriteEntity.id,
            title = favoriteEntity.title,
            voteAverage = favoriteEntity.voteAverage,
            imagePath = favoriteEntity.posterPath ?: "",
            overview = favoriteEntity.overview ?: "",
            type = if (favoriteEntity.type == CatalogType.MOVIE.id) CatalogType.MOVIE else CatalogType.TV_SHOW
        )

        fun fromCatalogDetailDomain(type: Int, catalogDetail: CatalogDetail) = FavoriteEntity(
            id = catalogDetail.id,
            type = type,
            title = catalogDetail.title,
            voteAverage = catalogDetail.voteAverage,
            overview = catalogDetail.overview,
            genres = catalogDetail.genres,
            releaseDate = catalogDetail.releaseDate,
            posterPath = catalogDetail.imagePath,
        )
    }
}
