package es.afmsoft.moviesapp

import android.app.Application
import androidx.room.Room
import es.afmsoft.moviesapp.model.database.MovieDatabase

class MoviesApp : Application() {
    lateinit var db: MovieDatabase
        private set

    override fun onCreate() {
        super.onCreate()

        db = Room.databaseBuilder(this, MovieDatabase::class.java, "movie-db").build()
    }
}