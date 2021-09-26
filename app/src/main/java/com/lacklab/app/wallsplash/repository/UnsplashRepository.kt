package com.lacklab.app.wallsplash.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.lacklab.app.wallsplash.api.ApiResponse
import com.lacklab.app.wallsplash.api.UnsplashService
import com.lacklab.app.wallsplash.data.UnsplashPhoto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class UnsplashRepository @Inject constructor(
    private val service: UnsplashService) {

    fun fetchPhotos(): Flow<LiveData<ApiResponse<List<UnsplashPhoto>>>> {
        return flow {
            val photos = service.getPhotos(1, NETWORK_PAGE_SIZE)
            emit(photos)
        }
    }

    fun getSearchResultStream(query: String): Flow<PagingData<UnsplashPhoto>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = true, pageSize = NETWORK_PAGE_SIZE),
            pagingSourceFactory = { UnsplashSearchPhotosPagingSource(service, query) }
        ).flow
    }


    companion object {
        private const val NETWORK_PAGE_SIZE = 10
    }
}