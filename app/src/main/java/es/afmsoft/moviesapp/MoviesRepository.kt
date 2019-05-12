package es.afmsoft.moviesapp

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MoviesRepository {

    private val tmdbClient = OkHttpClient().newBuilder()
        .addInterceptor(ServiceInterceptor())
        .build()

    private lateinit var moviesService: MoviesService

    init {
        val retrofit = Retrofit.Builder()
            .client(tmdbClient)
            .baseUrl("https://api.themoviedb.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()

        moviesService = retrofit.create(MoviesService::class.java)
    }

    fun discoverMovies(year: Int, countryCode: String) = moviesService.discoverMoviesAsync(year, countryCode)
}
