package es.afmsoft.moviesapp.data

import android.app.Application
import android.location.Geocoder
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import es.afmsoft.data.sources.LocationDataSource
import kotlin.coroutines.resume
import kotlinx.coroutines.suspendCancellableCoroutine

class GpsLocationDataSource(private val application: Application) : LocationDataSource {

    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(application)

    private val geocoder: Geocoder = Geocoder(application)

    override suspend fun findLastRegion(): String? {
        val loc = getLocation()
        return loc?.toRegion()
    }

    private suspend fun getLocation(): Location? =
        suspendCancellableCoroutine { cont ->
            fusedLocationClient.lastLocation
                .addOnCompleteListener { cont.resume(it.result) }
                .addOnFailureListener { cont.resume(null) }
        }

    private fun Location?.toRegion(): String? {
        val addresses = this?.let {
            geocoder.getFromLocation(latitude, longitude, 1)
        }
        return addresses?.firstOrNull()?.countryCode
    }
}
