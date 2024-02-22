package com.example.homework_25.presentation.screen.map

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.homework_25.R
import com.example.homework_25.databinding.FragmentMapsBinding
import com.example.homework_25.presentation.base.BaseFragment
import com.example.homework_25.presentation.dialog.MapSearchBottomSheetDialog
import com.example.homework_25.presentation.screen.map.event.MapsEvent
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MapsFragment : BaseFragment<FragmentMapsBinding>(FragmentMapsBinding::inflate),
    OnMapReadyCallback, MapSearchBottomSheetDialog.SearchListener {
    val viewModel: MapsFragmentViewModel by viewModels()

    private var googleMap: GoogleMap? = null
    private lateinit var dialog: MapSearchBottomSheetDialog

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
    }

    override fun bind() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
        currentLocationObserve()
    }

    override fun listener() {
        dialogShowListener()
        binding.btnCurrentLocation.setOnClickListener {
            viewModel.onEvent(MapsEvent.GetCurrentLocation)
            getCurrentLocation()
        }
    }

    private fun currentLocationObserve() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.currentLocationStateFlow.collect { state ->
                    state.success?.let {
                        val latLng = LatLng(it.latitude, it.longitude)
                        val markerOptions = MarkerOptions()
                            .position(latLng)
                        marker = googleMap?.addMarker(markerOptions)
                        googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10f))
                    }
                }
            }
        }
    }

    private fun requestLocationPermissions() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            101
        )
    }


    private fun getCurrentLocation() {
        val fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireContext())
        if (isLocationEnabled() && checkPermission()
        ) {
            fusedLocationProviderClient.lastLocation.addOnSuccessListener {
                val latLng = LatLng(it.latitude, it.longitude)
                Log.i("omiko", latLng.toString())
                val markerOptions = MarkerOptions()
                    .position(latLng)
                marker = googleMap?.addMarker(markerOptions)
                googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10f))
            }
        }else{
            requestLocationPermissions()
        }

    }

    private fun dialogShowListener() {
        binding.btnSearch.setOnClickListener {
            dialog = MapSearchBottomSheetDialog()
            dialog.setSearchListener(this)
            dialog.show(parentFragmentManager, "tag")
        }
    }


    private var marker: Marker? = null


    override fun onSearch(latLng: LatLng) {
        val markerOptions = MarkerOptions()
            .position(latLng)
        marker = googleMap?.addMarker(markerOptions)
        googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10f))
    }


    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    private fun checkPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) ==
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }
}