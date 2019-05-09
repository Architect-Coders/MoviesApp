package es.afmsoft.moviesapp


import android.Manifest
import android.annotation.SuppressLint
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.*
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.single.PermissionListener
import android.Manifest.permission
import com.karumi.dexter.Dexter
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.BasePermissionListener


class MainActivity : CoroutineScopeActivity() {

    private val adapter = MoviesAdapter {
        //TODO Start detail activity
    }

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val tmdbClient = OkHttpClient().newBuilder()
        .addInterceptor(ServiceInterceptor())
        .build()

    private lateinit var geocoder: Geocoder

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        geocoder = Geocoder(this)

        val retrofit = Retrofit.Builder()
            .client(tmdbClient)
            .baseUrl("https://api.themoviedb.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()

        val moviesService = retrofit.create(MoviesService::class.java)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        launch {

            var countryCode = getLastLocation()
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

    private suspend fun getLastLocation(): String = findLastLocation().toRegion()

    private suspend fun findLastLocation(): Location? {
        val success = checkLocationPermission()
        return if (success) getLocation() else null
    }

    private suspend  fun checkLocationPermission() : Boolean =
        suspendCancellableCoroutine  {cont ->
            Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(object : BasePermissionListener() {
                    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                        cont.resume(true)
                    }

                    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                        cont.resume(false)
                    }
                }).check()
        }

    @SuppressLint("MissingPermission")
    private suspend fun getLocation() : Location? =
        suspendCoroutine { cont ->
            fusedLocationClient.lastLocation
                .addOnCompleteListener { cont.resume(it.result) }
                .addOnFailureListener { cont.resume(null) }
            }

    private fun Location?.toRegion(): String {
        val addresses = this?.let {
            geocoder.getFromLocation(latitude, longitude, 1)
        }
        return addresses?.firstOrNull()?.countryCode ?: "US"
    }
}
