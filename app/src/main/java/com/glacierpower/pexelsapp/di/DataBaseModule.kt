package com.glacierpower.pexelsapp.di

import android.content.Context
import com.glacierpower.pexelsapp.data.dao.BookmarksDao
import com.glacierpower.pexelsapp.data.dao.BookmarksDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {
    @Provides
    fun provideBookmarksDataBase(context: Context): BookmarksDataBase {
        return BookmarksDataBase.getBookmarksDatabaseInstance(context)
    }

    @Provides
    fun provideBookmarksDao(bookmarksDataBase: BookmarksDataBase): BookmarksDao {
        return bookmarksDataBase.getBookmarksDao()
    }
}