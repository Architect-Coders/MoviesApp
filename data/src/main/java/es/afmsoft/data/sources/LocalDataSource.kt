package es.afmsoft.data.sources

import es.afmsoft.domain.Movie

interface LocalDataSource {
    suspend fun isEmpty(): Boolean
    suspend fun getPopularMovies(): List<Movie>
    suspend fun saveMovies(movies: List<Movie>)
    suspend fun getMovie(movieId: Int): Movie?
    suspend fun update(movie: Movie)
}