package com.example.weatherapptest.tools.location

import android.app.Activity
import android.location.Location
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface LocationManagerInteraction {
     fun getCurrentLocation(
        coroutineScope: CoroutineScope
    ): Flow<Location?>

    fun checkGpsStatus(): Boolean
    fun setActivity(activity: Activity)
    fun checkLocationPermissions(): Boolean
}