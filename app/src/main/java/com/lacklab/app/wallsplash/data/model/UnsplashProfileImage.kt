package com.lacklab.app.wallsplash.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class UnsplashProfileImage (
    val small: String?,
    val medium: String?,
    val large: String?,
) : Parcelable
