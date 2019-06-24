package es.afmsoft.moviesapp.data.server

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MovieServer {
    private val tmdbClient = OkHttpClient().newBuilder()
        .addInterceptor(ServiceInterceptor())
        .build()

    val service: MoviesService = Retrofit.Builder()
        .client(tmdbClient)
        .baseUrl("https://api.themoviedb.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build().run {
            create(MoviesService::class.java)
        }
}