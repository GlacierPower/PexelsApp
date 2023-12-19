package com.glacierpower.pexelsapp.data.service.response

data class PexelsResponse(
    var id: Int,
    var width: Int,
    var height: Int,
    var url: String,
    var photographer: String,
    var original: String,
    var large: String,
    var large2x: String,
    var medium: String,
    var small: String,
    var portrait: String,
    var landscape: String,
    var tiny: String
)
