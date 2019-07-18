package es.afmsoft.moviesapp.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Movie(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "backdrop_path") val backdropPath: String?,
<<<<<<< HEAD:app/src/main/java/es/afmsoft/moviesapp/data/database/Movie.kt
    @ColumnInfo(name = "poster_path") val posterPath: String?,
    val favourite: Boolean,
    val overview: String
=======
    @ColumnInfo(name = "poster_path") val posterPath: String?
>>>>>>> master:app/src/main/java/es/afmsoft/moviesapp/data/database/Movie.kt
)
