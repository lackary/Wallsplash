package com.lacklab.app.wallsplash.data.pagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.lacklab.app.wallsplash.data.api.*
import com.lacklab.app.wallsplash.data.model.UnsplashPhoto
import com.lacklab.app.wallsplash.data.model.UnsplashPhotos
import com.lacklab.app.wallsplash.data.repo.UnsplashRepository.Companion.NETWORK_PAGE_SIZE
import timber.log.Timber

class UnsplashPhotosPagingSource (
    private val api: UnsplashApi,
): PagingSource<Int, UnsplashPhoto>() {
    override fun getRefreshKey(state: PagingState<Int, UnsplashPhoto>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnsplashPhoto> {
        Timber.d("load")
        val page = params.key ?: com.lacklab.app.wallsplash.util.UNSPLASH_STARTING_PAGE_INDEX
        var data: UnsplashPhotos? = null
        return try {
//            val response =
//                object: UnsplashMediator<List<UnsplashPhoto>, List<UnsplashPhoto>>(appExecutors) {
//                    override suspend fun createCall() = service.getPhotos(page, params.loadSize)
//                }.getData()
            val response = api.getPhotos(page, params.loadSize)
            when(response) {
                is ApiSuccessResponse -> {
//                    val apiSuccessResponse = response
                    data = response.totalPages?.let {
                        UnsplashPhotos(
                            results = response.body, totalPages = it
                        )
                    }
                }
                is ApiErrorResponse -> {
                    throw Exception(response.errorMessage)
                }
                is ApiEmptyResponse -> {
                    Timber.d("ApiEmptyResponse")
                }
            }

            // by default, init loadSize is 3 x NETWORK_PAGE_SIZE
            // Ensure we don't retrieve duplicating items at 2nd time request
            val nextPage = page + (params.loadSize / NETWORK_PAGE_SIZE)
            LoadResult.Page(
                data = data!!.results,
//                prevKey = if (page == UNSPLASH_STARTING_PAGE_INDEX) null else page - 1,
                prevKey = null,
                nextKey = if (page == data.totalPages) null else nextPage
            )
        } catch (e: Exception) {
            Timber.d("exception: ${e.message}")
            LoadResult.Error(e)
        }
    }
}