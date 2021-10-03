package com.lacklab.app.wallsplash.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.lacklab.app.wallsplash.data.UnsplashPhoto
import com.lacklab.app.wallsplash.repository.UnsplashRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val unsplashRepository: UnsplashRepository
) : ViewModel() {
    private var currentSearchResult: Flow<PagingData<UnsplashPhoto>>? = null

    fun searchPhotos(queryString:String): Flow<PagingData<UnsplashPhoto>> {
        val newResult: Flow<PagingData<UnsplashPhoto>> =
            unsplashRepository.getSearchPhotosStream(queryString).cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }
}