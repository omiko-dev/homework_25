package com.example.homework_25.presentation.mapper

import com.example.homework_25.domain.model.LocationModel
import com.example.homework_25.presentation.model.LocationUi

fun LocationModel.toPresenter() =
    LocationUi(
        longitude = longitude,
        latitude = latitude
    )