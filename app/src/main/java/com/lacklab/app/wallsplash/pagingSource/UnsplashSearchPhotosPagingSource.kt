package com.lacklab.app.wallsplash.pagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.lacklab.app.wallsplash.api.UnsplashService
import com.lacklab.app.wallsplash.data.UnsplashPhoto
import com.lacklab.app.wallsplash.repository.UnsplashRepository.Companion.NETWORK_PAGE_SIZE

private const val UNSPLASH_STARTING_PAGE_INDEX = 1

class UnsplashSearchPhotosPagingSource(
    private val service: UnsplashService,
    private val query: String
) : PagingSource<Int, UnsplashPhoto>() {
    override fun getRefreshKey(state: PagingState<Int, UnsplashPhoto>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnsplashPhoto> {
        val page = params.key ?: UNSPLASH_STARTING_PAGE_INDEX
        return try {
            val response = service.searchPhotos(query, page, params.loadSize)
            val photos = response.results

            // by default, init loadSize is 3 x NETWORK_PAGE_SIZE
            // Ensure we don't retrieve duplicating items at 2nd time request
            val nextPage = page + (params.loadSize / NETWORK_PAGE_SIZE)
            LoadResult.Page(
                data = photos,
//                prevKey = if (page == UNSPLASH_STARTING_PAGE_INDEX) null else page - 1,
                prevKey = null,
                nextKey = if (page == response.totalPages) null else nextPage
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }
}