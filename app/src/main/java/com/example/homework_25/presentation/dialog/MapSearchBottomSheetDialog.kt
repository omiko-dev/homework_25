package com.example.homework_25.presentation.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.homework_25.R
import com.example.homework_25.databinding.MapSearchBottomSheetBinding
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MapSearchBottomSheetDialog : BottomSheetDialogFragment() {
    private var _binding: MapSearchBottomSheetBinding? = null
    private val binding get() = _binding!!

    interface SearchListener {
        fun onSearch(latLng: LatLng)
    }

    private var searchListener: SearchListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MapSearchBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Places.initialize(requireContext(), "AIzaSyA1aZd4PNqSbA4JUGIYyctlEPBHAXoFIkY")
        val autocompleteFragment =
            childFragmentManager.findFragmentById(R.id.autocomplete_fragment)
                    as AutocompleteSupportFragment

        autocompleteFragment.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME))
        autocompleteFragment.setPlaceFields(
            listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG)
        )
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                place.latLng?.let {
                    searchListener?.onSearch(it)
                }
                dismiss()
            }

            override fun onError(status: Status) {
                dismiss()
            }
        })
    }

    fun setSearchListener(listener: SearchListener) {
        searchListener = listener
    }
}