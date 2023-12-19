package com.glacierpower.pexelsapp.utils

import android.app.DownloadManager
import android.content.Context
import android.os.Environment
import androidx.core.net.toUri
import com.glacierpower.pexelsapp.R
import javax.inject.Inject

class ImageDownloader @Inject constructor(
    private val context: Context
) : FileDownloader {
    private val downloadManager = context.getSystemService(DownloadManager::class.java)
    override fun downloadImage(url: String): Long {
        val request = DownloadManager.Request(url.toUri())
            .setMimeType(context.getString(R.string.mimeType))
            .setAllowedNetworkTypes(
                DownloadManager.Request.NETWORK_WIFI.and(DownloadManager.Request.NETWORK_MOBILE)
            )
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setTitle(context.getString(R.string.image))
            .setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS,
                context.getString(R.string.image_jpeg)
            )
            .setAllowedOverMetered(true)
        return downloadManager.enqueue(request)
    }

}