package com.glacierpower.pexelsapp.model


data class CollectionModel(
    val id:String,
    val title:String,
    val description:String?,
    val private:Boolean,
    val media_count:Int,
    val photos_count:Int,
    val videos_count:Int
)