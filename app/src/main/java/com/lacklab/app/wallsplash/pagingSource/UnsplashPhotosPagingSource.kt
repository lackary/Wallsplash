package com.lacklab.app.wallsplash.pagingSource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.lacklab.app.wallsplash.api.*
import com.lacklab.app.wallsplash.data.UnsplashPhoto
import com.lacklab.app.wallsplash.data.UnsplashSearchPhotos

private const val UNSPLASH_STARTING_PAGE_INDEX = 1

class UnsplashPhotosPagingSource (
    private val service: UnsplashService,
): PagingSource<Int, UnsplashPhoto>() {
    override fun getRefreshKey(state: PagingState<Int, UnsplashPhoto>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnsplashPhoto> {
        val page = params.key ?: UNSPLASH_STARTING_PAGE_INDEX
        var data: UnsplashSearchPhotos? = null
        return try {
            val response = service.getPhotos(page, params.loadSize)
            when(response) {
                is ApiSuccessResponse -> {
                    val apiSuccessResponse = response as ApiSuccessResponse
                    data = apiSuccessResponse.totalPages?.let {
                        UnsplashSearchPhotos(
                            results = apiSuccessResponse.body, totalPages = it
                        )
                    }
                    Log.i("Test", "ApiSuccessResponse")
                }
            }

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