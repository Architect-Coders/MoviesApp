package es.afmsoft.usecases

import es.afmsoft.data.repository.MoviesRepository
import es.afmsoft.domain.Movie

class GetMovie(private val moviesRepository: MoviesRepository) {
    suspend fun invoke(movieId: Int): Movie? = moviesRepository.getMovie(movieId)
}
