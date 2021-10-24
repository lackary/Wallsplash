package com.lacklab.app.wallsplash.viewmodels

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.lacklab.app.wallsplash.data.UnsplashPhoto
import com.lacklab.app.wallsplash.repository.UnsplashRepository
import kotlinx.coroutines.flow.Flow

class PhotosViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val unsplashRepository: UnsplashRepository
) : ViewModel() {
    private var allUnpslashPhotos: Flow<PagingData<UnsplashPhoto>>? =null
    private var unsplashPhotosLiveData = MutableLiveData<PagingData<UnsplashPhoto>>()

    fun getAllUnsplashPhotos() : Flow<PagingData<UnsplashPhoto>> {
        val newResult: Flow<PagingData<UnsplashPhoto>> =
            unsplashRepository.getPhotosStream().cachedIn(viewModelScope)
        allUnpslashPhotos = newResult
        return newResult
    }

    suspend fun getAllUnsplashPhotosLiveData() : LiveData<PagingData<UnsplashPhoto>> {
        val newResult: LiveData<PagingData<UnsplashPhoto>> =
            unsplashRepository.getPhotoStream().cachedIn(viewModelScope)
        unsplashPhotosLiveData.value = newResult.value
        return newResult
    }
}