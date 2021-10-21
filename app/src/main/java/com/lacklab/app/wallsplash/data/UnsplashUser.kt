package com.lacklab.app.wallsplash.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UnsplashUser(
    @field:SerializedName("id") val id: String,
    @field:SerializedName("updated_at") val updatedAt: String,
    @field:SerializedName("username") val userName: String,
    @field:SerializedName("name") val name: String,
    @field:SerializedName("first_name") val firstName: String,
    @field:SerializedName("last_name") val lastName: String,
    @field:SerializedName("location") val location: String,
    @field:SerializedName("profile_image") val profileImage: UnsplashProfileImage
) : Parcelable {}
