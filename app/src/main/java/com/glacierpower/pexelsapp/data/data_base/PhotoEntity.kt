package com.glacierpower.pexelsapp.data.data_base

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "photo_entity")
data class PhotoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val width: Int,
    val height: Int,
    val url: String,
    val photographer: String,
    val photographer_url: String,
    val photographer_id: Int,
    val avg_color: String,
    val liked: Boolean,
    val alt: String
) : Serializable
