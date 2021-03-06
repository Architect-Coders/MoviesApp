package es.afmsoft.moviesapp.data.server

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesService {

    @GET("/3/discover/movie?language=es-ES&region=es&sort_by=popularity.desc&page=1")
    fun discoverMoviesAsync(@Query("year") year: Int, @Query("region") countryCode: String):
            Deferred<Response<MoviesResult>>
}
