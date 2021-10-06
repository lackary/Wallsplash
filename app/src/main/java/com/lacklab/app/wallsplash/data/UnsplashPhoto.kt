package com.lacklab.app.wallsplash.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UnsplashPhoto(
    @field:SerializedName("id") val id: String,
    @field:SerializedName("created_at") val createdAt: String,
    @field:SerializedName("updated_at") val updatedAt: String,
    @field:SerializedName("promoted_at") val promotedAt: String,
    val width: Int,
    val height: Int,
    val color: String,
    @field:SerializedName("blur_hash") val blurHash: String,
    val description: String,
    @field:SerializedName("alt_description") val allDescription: String,
    val urls: UnsplashUrls,
    val unsplashLinks: UnsplashLinks,
//    // unKnown type or data, just now it is List or array
//    val categories
    val likes: Long,
    @field:SerializedName("liked_by_user") val likedByUser: Boolean,
//    more ...
    @field:SerializedName("user") val user: UnsplashUser,
) : Serializable
