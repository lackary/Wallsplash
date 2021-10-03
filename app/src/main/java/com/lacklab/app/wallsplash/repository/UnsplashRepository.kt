package com.lacklab.app.wallsplash.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.lacklab.app.wallsplash.AppExecutors
import com.lacklab.app.wallsplash.api.UnsplashService
import com.lacklab.app.wallsplash.data.UnsplashPhoto
import com.lacklab.app.wallsplash.pagingSource.UnsplashPhotosPagingSource
import com.lacklab.app.wallsplash.pagingSource.UnsplashSearchPhotosPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UnsplashRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val service: UnsplashService
) {

    fun getPhotosStream(): Flow<PagingData<UnsplashPhoto>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = true, pageSize = NETWORK_PAGE_SIZE),
            pagingSourceFactory = { UnsplashPhotosPagingSource(appExecutors, service) }
        ).flow
    }

    fun getSearchPhotosStream(query: String): Flow<PagingData<UnsplashPhoto>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = true, pageSize = NETWORK_PAGE_SIZE),
            pagingSourceFactory = { UnsplashSearchPhotosPagingSource(service, query) }
        ).flow
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 10
    }
}