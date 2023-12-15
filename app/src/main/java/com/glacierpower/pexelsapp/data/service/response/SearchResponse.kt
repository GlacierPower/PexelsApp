package com.glacierpower.pexelsapp.data.service.response

data class SearchResponse(
    val total_results:Int,
    val page:Int,
    val per_page:Int,
    val photos:List<PhotoList>,
    val next_page:String
)