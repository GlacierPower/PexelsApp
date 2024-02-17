package com.glacierpower.pexelsapp.data.repositoryImpl

import com.glacierpower.pexelsapp.data.dao.BookmarksDao
import com.glacierpower.pexelsapp.data.data_base.BookmarksEntity
import com.glacierpower.pexelsapp.data.data_base.PhotoEntity
import com.glacierpower.pexelsapp.data.mappers.toModel
import com.glacierpower.pexelsapp.data.service.PexelsApiService
import com.glacierpower.pexelsapp.domain.pexels.PexelsRepository
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
    private val bookmarksDao: BookmarksDao,
) : PexelsRepository {
    override suspend fun insertPhotoCuratedPhoto(page: Int) {
        val response = pexelsApiService.getCuratedPhoto(ITEM_NUMBER, page)
        return withContext(Dispatchers.IO) {
            response.body()!!.photos.map { photo ->
                val photoEntity = PhotoEntity(
                    photo.id,
                    photo.width,
                    photo.height,
                    photo.url,
                    photo.photographer,
                    photo.photographer_url,
                    photo.photographer_id,
                    photo.avg_color,
                    photo.liked,
                    photo.alt
                )
                bookmarksDao.insertToPhotoEntity(photoEntity)
            }
        }
    }

    override suspend fun insertSearchedPhoto(query: String, pageNumber: Int) {
        val searchResponse = pexelsApiService.getSearchedPhoto(query, ITEM_NUMBER, pageNumber)
        return withContext(Dispatchers.IO) {
            searchResponse.body()!!.photos.map { photoList ->
                val photoEntity = PhotoEntity(
                    photoList.id,
                    photoList.width,
                    photoList.height,
                    photoList.url,
                    photoList.photographer,
                    photoList.photographer_url,
                    photoList.photographer_id,
                    photoList.avg_color,
                    photoList.liked,
                    photoList.alt
                )
                bookmarksDao.insertToPhotoEntity(photoEntity)
            }

        }
    }


    override suspend fun getFeaturedCollections(
        page: Int,
        per_page: Int
    ): ResultState<List<CollectionModel>> {
        val featuredResponse = pexelsApiService.getFeaturedCollection(page, per_page)
        return withContext(Dispatchers.IO) {
            ResultState.Success(featuredResponse.body()!!.collections.map {
                it.toModel()
            })
        }
    }

    override suspend fun getCuratedPhoto(pageNumber: Int): ResultState<List<PhotoListModel>> {
        return withContext(Dispatchers.IO) {
            val curatedResponse = pexelsApiService.getCuratedPhoto(ITEM_NUMBER, pageNumber)
            ResultState.Success(curatedResponse.body()!!.photos.map {
                it.toModel()
            })
        }
    }

    override suspend fun getSearchedPhoto(
        query: String,
        pageNumber: Int
    ): ResultState<List<PhotoListModel>> {
        return withContext(Dispatchers.IO) {
            val searchResponse = pexelsApiService.getSearchedPhoto(query, ITEM_NUMBER, pageNumber)
            ResultState.Success(searchResponse.body()!!.photos.map {
                it.toModel()
            })
        }
    }

    override suspend fun getPhotoById(id: Int): ResultState<PhotoListModel> {
        return withContext(Dispatchers.IO) {
            val photoResponse = pexelsApiService.getPhotoById(id)
            ResultState.Success(photoResponse.body()!!.toModel())
        }
    }


    override suspend fun findPhotoById(id: Int): PhotoEntity {
        return withContext(Dispatchers.IO) {
            bookmarksDao.findPhotoById(id)
        }
    }

    override suspend fun insertPhotoToBookmarksDataBase(photoEntity: PhotoEntity) {
        return withContext(Dispatchers.IO) {
            bookmarksDao.insertToBookmarksEntity(
                BookmarksEntity(
                    photoEntity.id,
                    photoEntity.width,
                    photoEntity.height,
                    photoEntity.url,
                    photoEntity.photographer,
                    photoEntity.photographer_url,
                    photoEntity.photographer_id,
                    photoEntity.avg_color,
                    photoEntity.liked,
                    photoEntity.alt,
                    true
                )
            )
        }
    }


    override suspend fun getPhotosFromBookmarksDataBase(): Flow<List<BookmarksEntity>> {

        return withContext(Dispatchers.IO) {
            bookmarksDao.getFromBookmarksEntity()

        }

    }

    override suspend fun deleteFromBookmarks(id: Int) {
        return withContext(Dispatchers.IO) {
            bookmarksDao.deleteFromBookmarksEntity(id)
        }

    }



}