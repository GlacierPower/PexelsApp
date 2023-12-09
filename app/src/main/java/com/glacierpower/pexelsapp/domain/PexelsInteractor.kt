package com.glacierpower.pexelsapp.domain

import com.glacierpower.pexelsapp.data.data_base.BookmarksEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PexelsInteractor @Inject constructor(private val pexelsRepository: PexelsRepository) {

    suspend fun getCuratedPhotos(pageNumber: Int) {
        return pexelsRepository.getCuratedPhotos(pageNumber)

    }

    suspend fun getSearchedPhotos(query: String, pageNumber: Int) {
        return pexelsRepository.getSearchedPhotos(query, pageNumber)
    }

    suspend fun insertPhotoToBookmarksDataBase(id: Int) {
        val foundPhoto = pexelsRepository.findPhotoById(id)
        return pexelsRepository.insertPhotoToBookmarksDataBase(foundPhoto)
    }

    suspend fun getPhotosFromBookmarksDataBase(): Flow<List<BookmarksEntity>> {
        return pexelsRepository.getPhotosFromBookmarksDataBase()
    }
}

