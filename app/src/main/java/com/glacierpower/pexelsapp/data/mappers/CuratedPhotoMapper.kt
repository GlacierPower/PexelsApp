package com.glacierpower.pexelsapp.data.mappers

import com.glacierpower.pexelsapp.data.service.response.PhotoList
import com.glacierpower.pexelsapp.data.service.response.Srcs
import com.glacierpower.pexelsapp.model.PhotoListModel
import com.glacierpower.pexelsapp.model.SrcsModel


fun PhotoList.toModel(): PhotoListModel {
    return PhotoListModel(
        id,
        width,
        height,
        url,
        photographer,
        photographer_url,
        photographer_id,
        avg_color,
        src.toModel(),
        liked,
        alt
    )
}

fun Srcs.toModel(): SrcsModel {
    return SrcsModel(
        original, large2x, large, medium, small, portrait, landscape, tiny
    )
}