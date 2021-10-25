package com.lacklab.app.wallsplash.data

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UnsplashCollection(
    val id: String,
    val title: String?,
    val description: String?,
    @field:SerializedName("published_at")
    val publishedAt: String?,
    @field:SerializedName("last_collected_at")
    val lastCollectedAt: String?,
    @field:SerializedName("updated_at")
    val updatedAt: String?,
    val curated: Boolean?,
    val featured: Boolean?,
    @field:SerializedName("total_photos")
    val totalPhotos: Int,
    val `private`: Boolean?,
    @field:SerializedName("share_key")
    val shareKey: String?,
    val links: UnsplashLinks?,
    val user: UnsplashUser?,
    @field:SerializedName("cover_photo")
    val coverPhoto: UnsplashPhoto
) : Parcelable