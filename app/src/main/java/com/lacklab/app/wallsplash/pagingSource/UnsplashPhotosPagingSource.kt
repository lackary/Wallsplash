package com.lacklab.app.wallsplash.pagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.lacklab.app.wallsplash.api.ApiResponse
import com.lacklab.app.wallsplash.api.UnsplashService
import com.lacklab.app.wallsplash.data.UnsplashPhoto
import com.lacklab.app.wallsplash.data.UnsplashSearchPhotos
import com.lacklab.app.wallsplash.repository.UnsplashMediator

private const val UNSPLASH_STARTING_PAGE_INDEX = 1

class UnsplashPhotosPagingSource (
    private val service: UnsplashService,
): PagingSource<Int, UnsplashPhoto>() {
    override fun getRefreshKey(state: PagingState<Int, UnsplashPhoto>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnsplashPhoto> {
        val page = params.key ?: UNSPLASH_STARTING_PAGE_INDEX
        return try {
            val result = service.getPhotos(page, params.loadSize)
            val data: UnsplashSearchPhotos? = null
            LoadResult.Page(
                data = data!!.results,
                prevKey = if (page == UNSPLASH_STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (page == data.totalPages) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}