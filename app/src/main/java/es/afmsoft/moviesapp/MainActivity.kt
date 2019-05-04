package es.afmsoft.moviesapp


import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.*
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : CoroutineScopeActivity() {

    private val adapter = MoviesAdapter {
        //TODO Start detail activity
    }

    private val tmdbClient = OkHttpClient().newBuilder()
        .addInterceptor(ServiceInterceptor())
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit = Retrofit.Builder()
            .client(tmdbClient)
            .baseUrl("https://api.themoviedb.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()

        val moviesService = retrofit.create(MoviesService::class.java)

        launch {
            val response = moviesService.discoverMovies(2019).await()
            if (response.isSuccessful) {
                Toast.makeText(this@MainActivity, "Petición correcta", Toast.LENGTH_LONG).show()
                val movies = response.body()!!
                adapter.movies = movies.results
                adapter.notifyDataSetChanged()
            } else {
                Toast.makeText(this@MainActivity, "Petición fallida", Toast.LENGTH_LONG).show()
            }
        }

        moviesGrid.layoutManager = GridLayoutManager(this, 3)
        moviesGrid.adapter = adapter
    }
}