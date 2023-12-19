package com.glacierpower.pexelsapp.data.repositoryImpl

import com.glacierpower.pexelsapp.data.dao.BookmarksDao
import com.glacierpower.pexelsapp.data.data_base.BookmarksEntity
import com.glacierpower.pexelsapp.data.service.PexelsApiService
import com.glacierpower.pexelsapp.domain.PexelsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PexelsRepositoryImpl @Inject constructor(
    private val pexelsApiService: PexelsApiService,
    private val bookmarksDao: BookmarksDao
) : PexelsRepository {
    override suspend fun getCuratedPhotos(pageNumber: Int) {

    }

    override suspend fun getSearchedPhotos(query: String, pageNumber: Int) {

    }

    override suspend fun findPhotoById(id: Int): BookmarksEntity {

    }

    override suspend fun insertPhotoToBookmarksDataBase(bookmarksEntity: BookmarksEntity) {

    }

    override suspend fun getPhotosFromBookmarksDataBase(): Flow<List<BookmarksEntity>> {

    }

}