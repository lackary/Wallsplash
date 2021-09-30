package com.lacklab.app.wallsplash.viewmodels

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.lacklab.app.wallsplash.data.UnsplashPhoto
import com.lacklab.app.wallsplash.repository.UnsplashRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val unsplashRepository: UnsplashRepository
): ViewModel() {

    private var allUnpslashPhotos: Flow<PagingData<UnsplashPhoto>>? =null

    private var currentQueryValue: String? = null

    private var currentSearchResult: Flow<PagingData<UnsplashPhoto>>? = null

    fun getAllUnsplashPhotos() : Flow<PagingData<UnsplashPhoto>> {
        val newResult: Flow<PagingData<UnsplashPhoto>> =
            unsplashRepository.fetchPhotos().cachedIn(viewModelScope)
        allUnpslashPhotos = newResult
        return newResult
    }

    fun getPhotos(): Flow<PagingData<UnsplashPhoto>> {
        currentQueryValue = "Japan"
        val newResult: Flow<PagingData<UnsplashPhoto>> =
            unsplashRepository.getSearchResultStream(currentQueryValue!!).cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }

    fun searchPhotos(queryString:String): Flow<PagingData<UnsplashPhoto>> {
        val newResult: Flow<PagingData<UnsplashPhoto>> =
            unsplashRepository.getSearchResultStream(queryString).cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }
}