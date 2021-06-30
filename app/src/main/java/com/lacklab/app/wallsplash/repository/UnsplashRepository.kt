package com.lacklab.app.wallsplash.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.lacklab.app.wallsplash.api.ApiResponse
import com.lacklab.app.wallsplash.api.UnsplashService
import com.lacklab.app.wallsplash.data.UnsplashPagingSource
import com.lacklab.app.wallsplash.data.UnsplashPhoto
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class UnsplashRepository @Inject constructor(
    private val service: UnsplashService) {
    fun getSearchResultStream(query: String): Flow<PagingData<UnsplashPhoto>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = NETWORK_PAGE_SIZE),
            pagingSourceFactory = { UnsplashPagingSource(service, query) }
        ).flow
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 10
    }
}