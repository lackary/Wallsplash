package com.lacklab.app.wallsplash.data

import com.google.gson.annotations.SerializedName

data class UnsplashUser(
    @field:SerializedName("id") val id: String,
    @field:SerializedName("updated_at") val updatedAt: String,
    @field:SerializedName("username") val userName: String,
    @field:SerializedName("name") val name: String,
    @field:SerializedName("first_name") val firstName: String,
    @field:SerializedName("last_name") val lastName: String,
    @field:SerializedName("location") val location: String,
)
