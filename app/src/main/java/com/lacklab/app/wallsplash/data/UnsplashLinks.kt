package com.lacklab.app.wallsplash.data

import com.google.gson.annotations.SerializedName

data class UnsplashLinks(
    val self: String,
    val html: String,
    val download: String,
    @field:SerializedName("download_location") val downloadLocation: String,
) {}
