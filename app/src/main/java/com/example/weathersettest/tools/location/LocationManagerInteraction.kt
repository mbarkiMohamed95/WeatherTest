package com.example.weatherapptest.tools.location

import android.app.Activity
import android.location.Location
import kotlinx.coroutines.CoroutineScope

interface LocationManagerInteraction {
   suspend fun getCurrentLocation(
       coroutineScope: CoroutineScope,
       callback:suspend (Location)->Unit)
    fun checkGpsStatus(): Boolean
    fun setActivity(activity: Activity)
    fun checkLocationPermissions(): Boolean
}