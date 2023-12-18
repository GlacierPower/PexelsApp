package com.glacierpower.pexelsapp.utils

interface FileDownloader {
    fun downloadImage(url: String): Long
}