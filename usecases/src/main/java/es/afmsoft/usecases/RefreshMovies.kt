package es.afmsoft.usecases

import es.afmsoft.data.repository.MoviesRepository
import es.afmsoft.domain.Movie

class RefreshMovies(private val moviesRepository: MoviesRepository) {
    suspend fun invoke(): List<Movie> = moviesRepository.refreshMovies()
}
