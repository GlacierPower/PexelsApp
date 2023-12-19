package com.glacierpower.pexelsapp.data.service

import com.glacierpower.pexelsapp.data.service.response.CuratedPhotoResponse
import com.glacierpower.pexelsapp.data.service.response.FeaturedCollectionResponse
import com.glacierpower.pexelsapp.data.service.response.PhotoList
import com.glacierpower.pexelsapp.data.service.response.SearchResponse
import com.glacierpower.pexelsapp.utils.Constants.API_KEY
import com.glacierpower.pexelsapp.utils.Constants.AUTHORIZATION
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface PexelsApiService {


    @Headers("$AUTHORIZATION $API_KEY")
    @GET("photos/{id}")
    suspend fun getPhotoById(@Path("id") id: Int): Response<PhotoList>

    @Headers("$AUTHORIZATION $API_KEY")
    @GET("curated")
    suspend fun getCuratedPhoto(
        @Query("per_page") itemNum: Int,
        @Query("page") numPage: Int
    ): Response<CuratedPhotoResponse>

    @Headers("$AUTHORIZATION $API_KEY")
    @GET("search")
    suspend fun getSearchedPhoto(
        @Query("query") query: String?,
        @Query("per_page") itemNum: Int,
        @Query("page") numPage: Int
    ): Response<SearchResponse>


    @Headers("$AUTHORIZATION $API_KEY")
    @GET("collections/featured")
    suspend fun getFeaturedCollection(
        @Query("page") page: Int,
        @Query("per_page") itemNum: Int,
    ): Response<FeaturedCollectionResponse>
}