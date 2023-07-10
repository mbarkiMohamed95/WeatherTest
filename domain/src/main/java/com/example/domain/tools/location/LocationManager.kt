package com.example.domain.tools.location

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.*
import android.location.LocationManager
import android.util.Log
import androidx.core.content.ContextCompat
import com.example.weatherapptest.tools.location.LocationManagerInteraction
import com.google.android.gms.location.*
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationManager @Inject constructor(@ApplicationContext private val context: Context) :
    LocationManagerInteraction {
    private var locationManager: LocationManager? = null
    private var fusedLocationProviderClient: FusedLocationProviderClient? = null
    private val TAG: String = javaClass.name
    private var currentActivity: Activity? = null

    init {
        locationManager =
            context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    }

    private fun getFranceLocation(): Location {
        val location = Location("")
        location.latitude = 46.528639
        location.longitude = 2.438972
        return location
    }

    override fun checkLocationPermissions(): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    }

    /**
     * check GPS
     */
    override fun checkGpsStatus(): Boolean {
        var locationManager =
            context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    override fun setActivity(activity: Activity) {
        currentActivity = activity
    }

    override  fun getCurrentLocation(
        coroutineScope: CoroutineScope
    ): Flow<Location?> = callbackFlow {
        try {
            if (checkGpsStatus()) {
                if (!checkLocationPermissions()) {
                    val locationResult = fusedLocationProviderClient?.lastLocation
                    locationResult?.addOnCompleteListener(currentActivity ?: return@callbackFlow) { task ->
                        coroutineScope.launch {
                            if (task.isSuccessful) {
                                // Set the map's camera position to the current location of the device.
                                task.result?.let {
                                    send(it)
                                } ?: kotlin.run {
                                    getLastKnownLocation(context)?.let { lastLocation ->
                                        send(lastLocation)
                                    }
                                }
                            } else {
                                getLastKnownLocation(context)?.let { lastLocation ->
                                    send(lastLocation)
                                }
                            }
                        }
                    }
                } else {
                    send(null)
                }
            } else {
                send(null)
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
        awaitClose()
    }

    private fun getLastKnownLocation(context: Context): Location? {
        if (!checkLocationPermissions()) {
            val mLocationManager =
                context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val providers = mLocationManager.getProviders(true)
            var bestLocation: Location? = null
            for (provider in providers) {
                @SuppressLint("MissingPermission") val location =
                    mLocationManager.getLastKnownLocation(provider)
                        ?: continue
                if (bestLocation == null || location.accuracy < bestLocation.accuracy) {
                    bestLocation = location
                }
            }
            var ress = Location("")
            ress.latitude = 48.866667
            ress.longitude = 2.333333
            return bestLocation ?: ress
        } else {
            return null
        }
    }

}