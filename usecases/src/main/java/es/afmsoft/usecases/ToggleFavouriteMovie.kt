package es.afmsoft.usecases

import es.afmsoft.data.repository.MoviesRepository
import es.afmsoft.domain.Movie

class ToggleFavouriteMovie(private val moviesRepository: MoviesRepository) {
    suspend fun invoke(movie: Movie): Movie = with(movie) {
        copy(favourite = !this.favourite).also { moviesRepository.update(it) }
    }
}
