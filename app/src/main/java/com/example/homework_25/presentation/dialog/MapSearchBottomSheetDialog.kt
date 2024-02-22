package com.example.homework_25.presentation.dialog

import android.content.Context
import android.os.Bundle
import android.util.Log.i
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.homework_25.databinding.MapSearchBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MapSearchBottomSheetDialog() : BottomSheetDialogFragment() {
    private var _binding: MapSearchBottomSheetBinding? = null
    private val binding get() = _binding!!

    interface SearchListener {
        fun onSearch(countryName: String)
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
        binding.btnSearch.setOnClickListener {
            val countryName = binding.etSearch.text.toString()
            searchListener?.onSearch(countryName)
            dismiss()
        }
    }

    fun setSearchListener(listener: SearchListener) {
        searchListener = listener
    }
}