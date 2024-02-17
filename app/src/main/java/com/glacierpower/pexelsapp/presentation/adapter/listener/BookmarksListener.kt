package com.glacierpower.pexelsapp.presentation.adapter.listener

interface BookmarksListener {
    fun getPhotoById(id: Int, link: String)

    fun deleteFormBookmarks(id: Int)
}