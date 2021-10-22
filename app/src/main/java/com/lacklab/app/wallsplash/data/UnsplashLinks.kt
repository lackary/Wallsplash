package com.lacklab.app.wallsplash.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UnsplashLinks(
    val self: String?,
    val html: String?,
    val download: String?,
    @field:SerializedName("download_location") val downloadLocation: String?,
) : Parcelable {}
