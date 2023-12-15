package com.glacierpower.pexelsapp.data.mappers

import com.glacierpower.pexelsapp.data.service.response.Collections
import com.glacierpower.pexelsapp.data.service.response.Src
import com.glacierpower.pexelsapp.model.CollectionModel
import com.glacierpower.pexelsapp.model.SrcModel


fun Collections.toModel(): CollectionModel {
    return CollectionModel(
        id, title, description, private, media_count, photos_count, videos_count
    )
}

fun Src.toModel(): SrcModel {
    return SrcModel(
        original, large2x, large, medium, small, portrait, landscape, tiny
    )
}