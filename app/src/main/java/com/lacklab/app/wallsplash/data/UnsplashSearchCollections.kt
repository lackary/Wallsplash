package com.lacklab.app.wallsplash.data

import com.google.gson.annotations.SerializedName

data class UnsplashSearchCollections(
    @field:SerializedName("total_pages") val totalPages: Int,
    val results: List<UnsplashCollection>
)
