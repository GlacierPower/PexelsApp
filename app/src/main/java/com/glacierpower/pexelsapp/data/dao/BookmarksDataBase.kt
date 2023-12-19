package com.glacierpower.pexelsapp.data.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.glacierpower.pexelsapp.data.data_base.BookmarksEntity
import com.glacierpower.pexelsapp.data.data_base.PhotoEntity

@Database(entities = [BookmarksEntity::class, PhotoEntity::class], version = 5, exportSchema = false)
abstract class BookmarksDataBase : RoomDatabase() {

    abstract fun getBookmarksDao(): BookmarksDao

    companion object {

        private const val DATABASE_NAME = "bookmarksDataBase"
        private var DB_INSTANCE: BookmarksDataBase? = null

        fun getBookmarksDatabaseInstance(context: Context): BookmarksDataBase {
            return DB_INSTANCE ?: Room
                .databaseBuilder(
                    context.applicationContext,
                    BookmarksDataBase::class.java,
                    DATABASE_NAME
                )
                .addMigrations()
                .build()
                .also { DB_INSTANCE = it }
        }
    }
}