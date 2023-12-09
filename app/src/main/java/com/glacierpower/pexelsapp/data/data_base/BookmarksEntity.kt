package com.glacierpower.pexelsapp.data.data_base

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "bookmarksEntity")
data class BookmarksEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var width: Int,
    var height: Int,
    var url: String,
    var photographer: String,
    var original: String,
    var large: String,
    var large2x: String,
    var medium: String,
    var small: String,
    var portrait: String,
    var landscape: String,
    var tiny: String
) : Serializable