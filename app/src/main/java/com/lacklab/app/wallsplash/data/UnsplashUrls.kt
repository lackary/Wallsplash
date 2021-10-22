package com.lacklab.app.wallsplash.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UnsplashUrls(
   val raw: String?,
   val full: String?,
   val regular: String?,
   val small: String?,
   val thumb: String?,
) : Parcelable
