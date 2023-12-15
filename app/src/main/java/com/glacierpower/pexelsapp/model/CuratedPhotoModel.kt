package com.glacierpower.pexelsapp.model


data class PhotoListModel(
    val id: Int,
    val width:Int,
    val height:Int,
    val url:String,
    val photographer:String,
    val photographer_url:String,
    val photographer_id:Int,
    val avg_color:String,
    val src:SrcsModel,
    val liked:Boolean,
    val alt:String
)

data class SrcsModel(
    val original: String,
    val large2x: String,
    val large: String,
    val medium: String,
    val small: String,
    val portrait: String,
    val landscape: String,
    val tiny: String
)
