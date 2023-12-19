package com.glacierpower.pexelsapp.data.service.response

data class CuratedPhotoResponse(
    val page: Int,
    val per_page: Int,
    val photos: List<PhotoList>,
    val next_page: String
)

data class PhotoList(
    val id: Int,
    val width:Int,
    val height:Int,
    val url:String,
    val photographer:String,
    val photographer_url:String,
    val photographer_id:Int,
    val avg_color:String,
    val src:Srcs,
    val liked:Boolean,
    val alt:String
    )

data class Srcs(
    val original: String,
    val large2x: String,
    val large: String,
    val medium: String,
    val small: String,
    val portrait: String,
    val landscape: String,
    val tiny: String
)