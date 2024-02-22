package com.example.homework_25.data.datasource

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.util.Log
import androidx.core.app.ActivityCompat
import com.example.homework_25.data.model.LocationDto
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CurrentLocationDataSourceImpl @Inject constructor(
    private val context: Context,
) : CurrentLocationDataSource {

    private val _currentLocationStateFlow = MutableStateFlow(LocationDto())
    override val currentLocationStateFlow get() = _currentLocationStateFlow.asStateFlow()

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    private fun checkPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) ==
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    override suspend fun updateCurrentLocation() = callbackFlow<LocationDto> {
        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

        if (checkPermission() && isLocationEnabled()) {
            fusedLocationProviderClient.lastLocation.addOnCanceledListener {
            }.addOnSuccessListener { location ->
                flow<LocationDto> {
                    _currentLocationStateFlow.emit(LocationDto(location.longitude, location.latitude))
                }
            }.addOnFailureListener {
                Log.i("omiko", it.message ?: "")
            }
        }

        awaitClose {  }
    }


}