package es.afmsoft.moviesapp

import es.afmsoft.moviesapp.model.MoviesResult
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesService {

    @GET("/3/discover/movie?language=es-ES&region=es&sort_by=popularity.desc&page=1")
    fun discoverMovies(@Query("year") year: Int, @Query("region") countryCode: String) : Deferred<Response<MoviesResult>>
}
