package es.afmsoft.moviesapp.data.database

import es.afmsoft.data.sources.LocalDataSource
import es.afmsoft.domain.Movie
import es.afmsoft.moviesapp.data.database.Movie as MovieDb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RoomDataSource(db: MovieDatabase) : LocalDataSource {

    private val movieDao = db.movieDao()

    override suspend fun getPopularMovies() = withContext(Dispatchers.IO) {
        movieDao.getAll().map(MovieDb::convertToDomainMovie)
    }

    override suspend fun saveMovies(movies: List<Movie>) = withContext(Dispatchers.IO) {
        movieDao.insertMovies(movies.map(Movie::convertToBdMovie))
    }

    override suspend fun isEmpty() = withContext(Dispatchers.IO) {
        movieDao.moviesCount() == 0
    }

    override suspend fun getMovie(movieId: Int) = withContext(Dispatchers.IO) {
        movieDao.getMovie(movieId)?.convertToDomainMovie()
    }
}

private fun Movie.convertToBdMovie() =
    MovieDb(0, title, backdropPath, posterPath, favourite)

private fun MovieDb.convertToDomainMovie() =
    Movie(id, title!!, posterPath, backdropPath, favourite)
