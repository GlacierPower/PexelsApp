package com.glacierpower.pexelsapp.domain

import com.glacierpower.pexelsapp.data.data_base.BookmarksEntity
import kotlinx.coroutines.flow.Flow


interface PexelsRepository {

    suspend fun getCuratedPhotos(pageNumber: Int)

    suspend fun getSearchedPhotos(query: String, pageNumber: Int)

    suspend fun findPhotoById(id: Int): BookmarksEntity

    suspend fun insertPhotoToBookmarksDataBase(bookmarksEntity: BookmarksEntity)

    suspend fun getPhotosFromBookmarksDataBase(): Flow<List<BookmarksEntity>>

}