package es.afmsoft.moviesapp.data.server

import es.afmsoft.data.repository.RegionRepository
import es.afmsoft.data.sources.RemoteDataSource
import es.afmsoft.domain.Movie
import es.afmsoft.moviesapp.data.server.Movie as MovieApi

class ServerDataSource : RemoteDataSource {

    override suspend fun getPopularMovies(apiKey: String, regionRepository: RegionRepository): List<Movie> {
        var serverMovies = listOf<Movie>()
        val response = MovieServer.service.discoverMoviesAsync(2019, regionRepository.findLastRegion())
        with(response.await()) {
            if (isSuccessful) {
                val movies = body()?.results
                if (movies != null) {
                    serverMovies = movies.map(MovieApi::convertToDomainMovie)
                }
            }
        }

        return serverMovies
    }
}

private fun MovieApi.convertToDomainMovie() =
    Movie(id, title, posterPath, backdropPath)
