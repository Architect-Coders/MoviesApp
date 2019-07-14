package es.afmsoft.moviesapp.ui.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    val id: Int,
    val title: String,
    val backdropPath: String?,
    val posterPath: String?
) : Parcelable
