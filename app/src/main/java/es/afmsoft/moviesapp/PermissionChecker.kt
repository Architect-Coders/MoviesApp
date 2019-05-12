package es.afmsoft.moviesapp

import androidx.appcompat.app.AppCompatActivity
import com.karumi.dexter.Dexter
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.single.BasePermissionListener
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

object PermissionChecker {

    suspend fun execute(activity: AppCompatActivity, permission: String): Boolean =
        suspendCancellableCoroutine { cont ->
            Dexter.withActivity(activity)
                .withPermission(permission)
                .withListener(object : BasePermissionListener() {
                    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                        cont.resume(true)
                    }

                    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                        cont.resume(false)
                    }
                }).check()
        }
}
