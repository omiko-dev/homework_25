package com.example.homework_25.data.mapper

import com.example.homework_25.data.model.LocationDto
import com.example.homework_25.domain.model.LocationModel

fun LocationDto.toDomain() =
    LocationModel(
        longitude = longitude!!,
        latitude = latitude!!
    )