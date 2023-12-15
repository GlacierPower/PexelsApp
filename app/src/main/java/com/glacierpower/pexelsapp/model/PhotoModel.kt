package com.glacierpower.pexelsapp.model

data class PhotoResponseModel(
    val nextPage: String,
    val page: Int,
    val perPage: Int,
    val photos: List<PhotoModel>,
)

data class PhotoModel(
    var id: Int,
    var width: Int,
    var height: Int,
    var url: String,
    var photographer: String?,
    var original: String,
    var large: String,
    var large2x: String,
    var medium: String,
    var small: String,
    var portrait: String,
    var landscape: String,
    var tiny: String
)

data class SrcModel(
    val original: String,
    val large2x: String,
    val large:String,
    val medium:String,
    val small:String,
    val portrait:String,
    val landscape:String,
    val tiny:String
)