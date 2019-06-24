package es.afmsoft.moviesapp

import android.app.Application
import androidx.room.Room
import es.afmsoft.moviesapp.data.database.MovieDatabase

class MoviesApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initDI()
    }
}
