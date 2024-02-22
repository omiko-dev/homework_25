package com.example.homework_25.presentation.screen.map

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


    private var marker: Marker? = null



    override fun onSearch(latLng: LatLng) {
        val markerOptions = MarkerOptions()
            .position(latLng)
        marker = googleMap?.addMarker(markerOptions)
        googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10f))
    }
}