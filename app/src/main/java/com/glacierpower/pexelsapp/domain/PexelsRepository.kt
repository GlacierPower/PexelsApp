package com.glacierpower.pexelsapp.domain

import com.glacierpower.pexelsapp.data.data_base.BookmarksEntity
import com.glacierpower.pexelsapp.data.data_base.PhotoEntity
import com.glacierpower.pexelsapp.model.CollectionModel
import com.glacierpower.pexelsapp.model.PhotoListModel
import com.glacierpower.pexelsapp.utils.ResultState
import kotlinx.coroutines.flow.Flow


interface PexelsRepository {


    suspend fun getFeaturedCollections(page: Int, per_page: Int): ResultState<List<CollectionModel>>

    suspend fun getCuratedPhoto(pageNumber: Int): ResultState<List<PhotoListModel>>

    suspend fun getSearchedPhoto(query: String, pageNumber: Int): ResultState<List<PhotoListModel>>

    suspend fun getPhotoById(id: Int): ResultState<PhotoListModel>


    suspend fun insertPhotoCuratedPhoto(page: Int)

    suspend fun insertSearchedPhoto(
        query: String,
        pageNumber: Int
    )

    suspend fun findPhotoById(id: Int): PhotoEntity

    suspend fun insertPhotoToBookmarksDataBase(photoEntity: PhotoEntity)

    suspend fun getPhotosFromBookmarksDataBase():Flow<List<BookmarksEntity>>

}