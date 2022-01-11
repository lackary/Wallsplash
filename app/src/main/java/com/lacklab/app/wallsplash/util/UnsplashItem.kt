package com.lacklab.app.wallsplash.util

import com.lacklab.app.wallsplash.data.model.UnsplashCollection
import com.lacklab.app.wallsplash.data.model.UnsplashPhoto

sealed class UnsplashItem {
    data class Collection(val data: UnsplashCollection): UnsplashItem()
    data class Photo(val data: UnsplashPhoto): UnsplashItem()
}
