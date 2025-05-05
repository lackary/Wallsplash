package com.lacklab.app.wallsplash.data.repo.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.lacklab.app.wallsplash.data.remote.ApiEmptyResponse
import com.lacklab.app.wallsplash.data.remote.ApiErrorResponse
import com.lacklab.app.wallsplash.data.remote.ApiSuccessResponse
import com.lacklab.app.wallsplash.data.remote.api.UnsplashApi
import com.lacklab.app.wallsplash.data.model.UnsplashCollections
import com.lacklab.app.wallsplash.util.UnsplashItem
import com.lacklab.app.wallsplash.data.repo.UnsplashRepository
import timber.log.Timber

class UnsplashCollectionsPagingSource(
    private val api: UnsplashApi,
) : PagingSource<Int, UnsplashItem>() {
    override fun getRefreshKey(state: PagingState<Int, UnsplashItem>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnsplashItem> {
        val page = params.key ?: com.lacklab.app.wallsplash.util.UNSPLASH_STARTING_PAGE_INDEX
        var data: UnsplashCollections? = null
        return try {
            val response = api.getCollections(page, params.loadSize)
            when(response) {
                is ApiSuccessResponse -> {
//                    val apiSuccessResponse = response
                    data = response.totalPages?.let {
                        UnsplashCollections(
                            results = response.body, totalPages = it
                        )
                    }
                    Timber.d("ApiSuccessResponse")
                }
                is ApiErrorResponse -> {
                    Timber.d("ApiErrorResponse: ${response.errorMessage}")
                    throw Exception(response.errorMessage)
                }
                is ApiEmptyResponse -> {
                    Timber.d("ApiEmptyResponse")
                }
            }
            val items = data!!.results.map{item -> UnsplashItem.Collection(item)}
            // by default, init loadSize is 3 x NETWORK_PAGE_SIZE
            // Ensure we don't retrieve duplicating items at 2nd time request
            val nextPage = page + (params.loadSize / UnsplashRepository.NETWORK_PAGE_SIZE)
            LoadResult.Page(
                data = items,
//                prevKey = if (page == UNSPLASH_STARTING_PAGE_INDEX) null else page - 1,
                prevKey = null,
                nextKey = if (page == data.totalPages) null else nextPage
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}