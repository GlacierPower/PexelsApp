package com.glacierpower.pexelsapp.presentation.adapter.listener

interface DetailsListener {
    fun downloadPhoto(link:String)
    fun addToBookmarks(id:Int)
}