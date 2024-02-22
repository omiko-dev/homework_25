package com.example.homework_25.presentation.screen.map

import android.location.Geocoder
import android.util.Log
import android.widget.Toast
import com.example.homework_25.R
import com.example.homework_25.databinding.FragmentMapsBinding
import com.example.homework_25.presentation.base.BaseFragment
import com.example.homework_25.presentation.dialog.MapSearchBottomSheetDialog
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

//@AndroidEntryPoint
class MapsFragment : BaseFragment<FragmentMapsBinding>(FragmentMapsBinding::inflate),
    OnMapReadyCallback, MapSearchBottomSheetDialog.SearchListener {

    private var googleMap: GoogleMap? = null
    private lateinit var dialog: MapSearchBottomSheetDialog

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
    }

    override fun bind() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    override fun listener() {
        dialogShowListener()
    }

    private fun dialogShowListener() {
        binding.btnSearch.setOnClickListener {
            dialog = MapSearchBottomSheetDialog()
            dialog.setSearchListener(this) // Set the fragment as the listener
            dialog.show(parentFragmentManager, "tag")
        }
    }

    override fun onSearch(countryName: String) {
        googleMap?.let {
            searchPlace(countryName)
        } ?: Log.e("MapsFragment", "Google map not initialized yet")
    }

    private var marker: Marker? = null

    private fun searchPlace(place: String) {
        try {
            val geocoder = Geocoder(requireContext())
            val addresses = geocoder.getFromLocationName(place, 1)

            if (!addresses.isNullOrEmpty()) {
                marker?.remove()

                val location = LatLng(addresses[0].latitude, addresses[0].longitude)
                val markerOptions = MarkerOptions()
                    .position(location)
                    .title(place)
                marker = googleMap?.addMarker(markerOptions)
                googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 10f))
            } else {
                Toast.makeText(context, "Place not found", Toast.LENGTH_SHORT).show()
            }

        } catch (e: Exception) {
            Toast.makeText(context, "Place not found", Toast.LENGTH_SHORT).show()
        }
    }
}