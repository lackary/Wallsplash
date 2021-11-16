package com.lacklab.app.wallsplash.data.repo.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.lacklab.app.wallsplash.data.remote.api.ApiEmptyResponse
import com.lacklab.app.wallsplash.data.remote.api.ApiErrorResponse
import com.lacklab.app.wallsplash.data.remote.api.ApiSuccessResponse
import com.lacklab.app.wallsplash.data.remote.api.UnsplashApi
import com.lacklab.app.wallsplash.data.model.*
import com.lacklab.app.wallsplash.data.repo.UnsplashRepository

class UnsplashSearchCollectionsPagingSource(
    private val api: UnsplashApi,
    private val query: String
) : PagingSource<Int, UnsplashCollection>() {
    override fun getRefreshKey(state: PagingState<Int, UnsplashCollection>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnsplashCollection> {
        val page = params.key ?: com.lacklab.app.wallsplash.util.UNSPLASH_STARTING_PAGE_INDEX
        return try {
            val response = api.searchCollections(query, page, params.loadSize)
            var data: UnsplashSearchCollections? = null
            when(response) {
                is ApiSuccessResponse -> {
                    data = response.body
                }
                is ApiErrorResponse -> {
                    throw Exception(response.errorMessage)
                }
                is ApiEmptyResponse -> {
                    data = UnsplashSearchCollections(
                        0,
                        results = emptyList<UnsplashCollection>()
                    )
                }
            }

            // by default, init loadSize is 3 x NETWORK_PAGE_SIZE
            // Ensure we don't retrieve duplicating items at 2nd time request
            val nextPage = page + (params.loadSize / UnsplashRepository.NETWORK_PAGE_SIZE)
            LoadResult.Page(
                data = data.results,
//                prevKey = if (page == UNSPLASH_STARTING_PAGE_INDEX) null else page - 1,
                prevKey = null,
                nextKey = if (page == data.totalPages) null else nextPage
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }
}