package com.lacklab.app.wallsplash.data

import com.google.gson.annotations.SerializedName

data class UnsplashPhotos(
    val totalPages: Int,
    val results: List<UnsplashPhoto>
)
