package es.afmsoft.data.repository

import es.afmsoft.data.sources.LocalDataSource
import es.afmsoft.data.sources.RemoteDataSource
import es.afmsoft.domain.Movie

class MoviesRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val apiKey: String,
    private val regionRepository: RegionRepository
) {
    suspend fun getPopularMovies(): List<Movie> {
        if (localDataSource.isEmpty()) {
            val movies = remoteDataSource.getPopularMovies(apiKey, regionRepository)
            localDataSource.saveMovies(movies)
        }

        return localDataSource.getPopularMovies()
    }

    suspend fun getMovie(movieId: Int) = localDataSource.getMovie(movieId)

    suspend fun update(movie: Movie) = localDataSource.update(movie)
}
