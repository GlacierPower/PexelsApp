package com.glacierpower.pexelsapp.data.service

import com.glacierpower.pexelsapp.data.service.response.PexelsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PexelsApiService {


    @GET("curated")
    suspend fun getCurated(
        @Query("per_page") itemNum: Int,
        @Query("page") numPage: Int
    ): Response<List<PexelsResponse>>


    @GET("search")
    suspend fun getSearch(
        @Query("query") query: String?,
        @Query("per_page") itemNum: Int,
        @Query("page") numPage: Int
    ): Response<List<PexelsResponse>>

}