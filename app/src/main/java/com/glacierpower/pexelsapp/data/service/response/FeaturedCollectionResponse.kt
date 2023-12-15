package com.glacierpower.pexelsapp.data.service.response

import com.google.gson.annotations.SerializedName

data class FeaturedCollectionResponse (
    @SerializedName("collections")
    val collections : List<Collections>,
    @SerializedName("page")
    val page:Int,
    @SerializedName("per_page")
    val per_page:Int,
    @SerializedName("total_results")
    val total_results:Int,
    @SerializedName("next_page")
    val next_page:String,
    @SerializedName("prev_page")
    val prev_page:String
)
data class Collections(
    @SerializedName("id")
    val id:String,
    @SerializedName("title")
    val title:String,
    @SerializedName("description")
    val description:String,
    @SerializedName("private")
    val private:Boolean,
    @SerializedName("media_count")
    val media_count:Int,
    @SerializedName("photos_count")
    val photos_count:Int,
    @SerializedName("videos_count")
    val videos_count:Int
)