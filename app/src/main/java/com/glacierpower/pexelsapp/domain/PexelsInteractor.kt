package com.glacierpower.pexelsapp.domain

import com.glacierpower.pexelsapp.data.data_base.BookmarksEntity
import com.glacierpower.pexelsapp.model.CollectionModel
import com.glacierpower.pexelsapp.model.PhotoListModel
import com.glacierpower.pexelsapp.utils.ResultState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PexelsInteractor @Inject constructor(private val pexelsRepository: PexelsRepository) {

    suspend fun getFeaturedCollections(
        page: Int,
        per_page: Int
    ): ResultState<List<CollectionModel>> {
        return pexelsRepository.getFeaturedCollections(page, per_page)

    }

    suspend fun getPhotoById(id: Int): ResultState<PhotoListModel> {
        return pexelsRepository.getPhotoById(id)
    }

    suspend fun getSearchedPhoto(
        query: String,
        pageNumber: Int
    ): ResultState<List<PhotoListModel>> {
        return pexelsRepository.getSearchedPhoto(query, pageNumber)
    }

    suspend fun getCuratedPhoto(pageNumber: Int): ResultState<List<PhotoListModel>> {
        return pexelsRepository.getCuratedPhoto(pageNumber)
    }

    suspend fun insertPhotoToBookmarksDataBase(id: Int) {
        val foundPhoto = pexelsRepository.findPhotoById(id)
        return pexelsRepository.insertPhotoToBookmarksDataBase(foundPhoto)
    }

    suspend fun getPhotosFromBookmarksDataBase(): Flow<List<BookmarksEntity>> {
        return pexelsRepository.getPhotosFromBookmarksDataBase()
    }
}

