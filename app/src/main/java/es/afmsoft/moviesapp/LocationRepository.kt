package es.afmsoft.moviesapp

import android.Manifest
import android.location.Geocoder
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlin.coroutines.resume
import kotlinx.coroutines.suspendCancellableCoroutine

class LocationRepository(private val activity: AppCompatActivity) {
    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(activity)
    private val geocoder: Geocoder = Geocoder(activity)

    suspend fun getLastLocation(): String = findLastLocation().toRegion()

    private suspend fun findLastLocation(): Location? {
        val success = PermissionChecker.execute(activity, Manifest.permission.ACCESS_FINE_LOCATION)
        return if (success) getLocation() else null
    }

    private suspend fun getLocation(): Location? =
        suspendCancellableCoroutine { cont ->
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
