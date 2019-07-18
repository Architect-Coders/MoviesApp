package es.afmsoft.moviesapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Movie::class], version = 3)

abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao

    companion object {
        fun build(context: Context) = Room.databaseBuilder(
            context,
            MovieDatabase::class.java,
            "movie-db"
        ).build()
    }
}
