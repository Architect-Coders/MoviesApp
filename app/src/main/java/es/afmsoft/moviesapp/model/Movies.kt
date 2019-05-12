package es.afmsoft.moviesapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MoviesResult(
    val page: Int,
    @SerializedName("total_results") val totalResults: Int,
    @SerializedName("total_pages") val totalPages: Int,
    val results: List<Movie>

) : Parcelable

@Parcelize
data class Movie(
    @SerializedName("poster_path") val posterPath: String,
    val adult: Boolean,
    val overview: String,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("genre_ids") val genreIds: List<Int>,
    val id: Int,
    @SerializedName("original_title") val originalTitle: String,
    @SerializedName("original_language") val originalLanguage: String,
    val title: String,
    @SerializedName("backdrop_path") val backdropPath: String,
    val popularity: Double,
    @SerializedName("vote_count") val voteCount: Int,
    val video: Boolean,
    @SerializedName("vote_average") val voteAverage: Double
) : Parcelable

