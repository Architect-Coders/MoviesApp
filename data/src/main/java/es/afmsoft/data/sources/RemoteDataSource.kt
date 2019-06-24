package es.afmsoft.data.sources

import es.afmsoft.data.repository.RegionRepository
import es.afmsoft.domain.Movie

interface RemoteDataSource {
    suspend fun getPopularMovies(apiKey: String, regionRepository: RegionRepository): List<Movie>
}