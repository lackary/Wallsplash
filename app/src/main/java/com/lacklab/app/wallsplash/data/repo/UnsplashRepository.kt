package com.lacklab.app.wallsplash.data.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.lacklab.app.wallsplash.AppExecutors
import com.lacklab.app.wallsplash.data.remote.api.UnsplashApi
import com.lacklab.app.wallsplash.data.model.UnsplashCollection
import com.lacklab.app.wallsplash.util.UnsplashItem
import com.lacklab.app.wallsplash.data.model.UnsplashPhoto
import com.lacklab.app.wallsplash.data.repo.source.UnsplashCollectionsPagingSource
import com.lacklab.app.wallsplash.data.repo.source.UnsplashPhotosPagingSource
import com.lacklab.app.wallsplash.data.repo.source.UnsplashSearchCollectionsPagingSource
import com.lacklab.app.wallsplash.data.repo.source.UnsplashSearchPhotosPagingSource
import com.lacklab.app.wallsplash.util.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UnsplashRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val api: UnsplashApi
) {
    fun getPhotos(): Flow<PagingData<UnsplashItem>> {
        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, prefetchDistance = 3),
            pagingSourceFactory = { UnsplashPhotosPagingSource(api) }
        ).flow
    }

    suspend fun getPhoto(id: String): Flow<UiState<UnsplashPhoto>> = flow {
        emit(UiState.Loading<UnsplashPhoto>(null))
        try {
            val result = api.getPhoto(id)
            emit(UiState.Success<UnsplashPhoto>(data = result))
        } catch (ex: Exception) {
            emit(UiState.Error<UnsplashPhoto>(data = null, message = ex.message))
        }
    }

    fun getCollections(): Flow<PagingData<UnsplashItem>> {
       return Pager(
           config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, prefetchDistance = 3),
           pagingSourceFactory = {UnsplashCollectionsPagingSource(api)}
       ).flow
    }

    suspend fun getCollection(id: String): UnsplashCollection {
        return api.getCollection(id)
    }

    fun getSearchPhotosStream(query: String): Flow<PagingData<UnsplashItem>> {
        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, ),
            pagingSourceFactory = { UnsplashSearchPhotosPagingSource(api, query) }
        ).flow
    }

    fun getSearchCollectionsStream(query: String): Flow<PagingData<UnsplashItem>> {
        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE),
            pagingSourceFactory = {UnsplashSearchCollectionsPagingSource(api, query)}
        ).flow
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 10
    }
}