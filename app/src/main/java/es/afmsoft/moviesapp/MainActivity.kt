package es.afmsoft.moviesapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), Scope by Scope.Impl {

    private val adapter = MoviesAdapter {
        // TODO Start detail activity
    }

    private val moviesRepository = MoviesRepository()
    private lateinit var locationRepository: LocationRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initScope()
        locationRepository = LocationRepository(this)

        moviesGrid.layoutManager = GridLayoutManager(this, 3)
        moviesGrid.adapter = adapter

        refreshUI()
    }

    override fun onDestroy() {
        closeScope()
        super.onDestroy()
    }
    private fun refreshUI() {
        launch {

            var countryCode = locationRepository.getLastLocation()
            val response = moviesRepository.discoverMovies(2019, countryCode).await()
            if (response.isSuccessful) {
                Toast.makeText(this@MainActivity, "Petición correcta", Toast.LENGTH_LONG).show()
                val movies = response.body()!!
                adapter.movies = movies.results
                adapter.notifyDataSetChanged()
            } else {
                Toast.makeText(this@MainActivity, "Petición fallida", Toast.LENGTH_LONG).show()
            }
        }
    }
}
