package com.glacierpower.pexelsapp.data.service.response

import com.google.gson.annotations.SerializedName

data class PhotoResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("per_page")
    val perPage: Int,
    @SerializedName("photos")
    val photos: List<Photo>,
    @SerializedName("next_page")
    val nextPage: String,
)

data class Photo(
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

data class Src(
    val original: String,
    val large2x: String,
    val large: String,
    val medium: String,
    val small: String,
    val portrait: String,
    val landscape: String,
    val tiny: String
)
