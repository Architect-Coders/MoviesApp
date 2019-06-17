package es.afmsoft.moviesapp.model

import es.afmsoft.moviesapp.MoviesApp
import es.afmsoft.moviesapp.model.database.Movie as DbMovie
import es.afmsoft.moviesapp.model.server.Movie as ServerMovie
import es.afmsoft.moviesapp.model.server.MovieServer
import es.afmsoft.moviesapp.ui.model.Movie as UiMovie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MoviesRepository(application: MoviesApp) {

    private val db = application.db

    suspend fun discoverMovies(year: Int, countryCode: String) = withContext(Dispatchers.IO) {
        with(db.movieDao()) {
            if (moviesCount() <= 10) {

                val response = MovieServer.service.discoverMoviesAsync(year, countryCode).await()
                if (response.isSuccessful) {
                    val movies = response.body()?.results
                    if (movies != null) {
                        val dbMovies = movies.map(ServerMovie::convertToBdMovie)
                        insertMovies(dbMovies)
                    }
                }
            }
            getAll().map(DbMovie::convertToUiMovie)
        }
    }
}

private fun ServerMovie.convertToBdMovie() =
    DbMovie(0, title, backdropPath, posterPath)

private fun DbMovie.convertToUiMovie() =
    UiMovie(id, title!!, posterPath, backdropPath)