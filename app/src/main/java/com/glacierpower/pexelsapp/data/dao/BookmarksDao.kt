package com.glacierpower.pexelsapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.glacierpower.pexelsapp.data.data_base.BookmarksEntity
import com.glacierpower.pexelsapp.data.data_base.PhotoEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface BookmarksDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToBookmarksEntity(bookmarksEntity: BookmarksEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToPhotoEntity(photoEntity: PhotoEntity)

    @Query("SELECT * FROM bookmarksEntity")
    fun getFromBookmarksEntity(): Flow<List<BookmarksEntity>>

    @Query("DELETE FROM bookmarksEntity WHERE id =:id ")
    suspend fun deleteFromBookmarksEntity(id: Int)

    @Query("SELECT * FROM photo_entity WHERE id =:id")
    fun findPhotoById(id: Int): PhotoEntity


}