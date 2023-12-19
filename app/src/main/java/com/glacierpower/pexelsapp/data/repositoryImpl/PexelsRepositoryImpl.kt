package com.glacierpower.pexelsapp.data.repositoryImpl

import com.glacierpower.pexelsapp.data.dao.BookmarksDao
import com.glacierpower.pexelsapp.data.data_base.BookmarksEntity
import com.glacierpower.pexelsapp.data.mappers.toModel
import com.glacierpower.pexelsapp.data.service.PexelsApiService
import com.glacierpower.pexelsapp.domain.PexelsRepository
import com.glacierpower.pexelsapp.model.CollectionModel
import com.glacierpower.pexelsapp.model.PhotoListModel
import com.glacierpower.pexelsapp.utils.Constants.ITEM_NUMBER
import com.glacierpower.pexelsapp.utils.ResultState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PexelsRepositoryImpl @Inject constructor(
    private val pexelsApiService: PexelsApiService,
    private val bookmarksDao: BookmarksDao
) : PexelsRepository {


    override suspend fun getFeaturedCollections(
        page: Int,
        per_page: Int
    ): ResultState<List<CollectionModel>> {
        val response = pexelsApiService.getFeaturedCollection(page, per_page)
        return withContext(Dispatchers.IO) {
            ResultState.Success(response.body()!!.collections.map {
                it.toModel()
            })
        }
    }

    override suspend fun getCuratedPhoto(pageNumber: Int): ResultState<List<PhotoListModel>> {
        return withContext(Dispatchers.IO) {
            val response = pexelsApiService.getCuratedPhoto(ITEM_NUMBER, pageNumber)
            ResultState.Success(response.body()!!.photos.map {
                it.toModel()
            })
        }
    }

    override suspend fun getSearchedPhoto(
        query: String,
        pageNumber: Int
    ): ResultState<List<PhotoListModel>> {
        return withContext(Dispatchers.IO) {
            val response = pexelsApiService.getSearchedPhoto(query, ITEM_NUMBER, pageNumber)
            ResultState.Success(response.body()!!.photos.map {
                it.toModel()
            })
        }
    }


    override suspend fun findPhotoById(id: Int): BookmarksEntity {
        return withContext(Dispatchers.IO) {
            bookmarksDao.findNewsByTitle(id)
        }
    }

    override suspend fun insertPhotoToBookmarksDataBase(bookmarksEntity: BookmarksEntity) {
        return withContext(Dispatchers.IO) {
            bookmarksDao.insertToBookmarksEntity(
                bookmarksEntity
            )
        }
    }

    override suspend fun getPhotosFromBookmarksDataBase(): Flow<List<BookmarksEntity>> {
        return withContext(Dispatchers.IO) {
            bookmarksDao.getFromBookmarksEntity()
        }
    }

}