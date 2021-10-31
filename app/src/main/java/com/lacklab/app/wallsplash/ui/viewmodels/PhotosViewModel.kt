package com.lacklab.app.wallsplash.ui.viewmodels

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.lacklab.app.wallsplash.base.BaseViewModel
import com.lacklab.app.wallsplash.data.model.UnsplashPhoto
import com.lacklab.app.wallsplash.data.repository.UnsplashRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotosViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val unsplashRepository: UnsplashRepository
) : BaseViewModel() {
    private var allUnpslashPhotos: Flow<PagingData<UnsplashPhoto>>? =null
    private var unsplashPhotosLiveData = MutableLiveData<PagingData<UnsplashPhoto>>()
    private lateinit var _photoFlow: Flow<PagingData<UnsplashPhoto>>
    val photoFlow: Flow<PagingData<UnsplashPhoto>>
        get() = _photoFlow
    init {
        getPhotos()
    }

    private fun getPhotos() = launchPaging({
        unsplashRepository.getPhotosStream().cachedIn(viewModelScope)
    }, {
        _photoFlow = it
    })

//    private fun getPhotos() = viewModelScope.launch {
//        val result = unsplashRepository.getPhotosStream().cachedIn(viewModelScope)
//        _photoFlow = result
//    }

//    fun getAllUnsplashPhotos() : Flow<PagingData<UnsplashPhoto>> {
//        val newResult: Flow<PagingData<UnsplashPhoto>> =
//            unsplashRepository.getPhotosStream().cachedIn(viewModelScope)
//        allUnpslashPhotos = newResult
//        return newResult
//    }

    fun getAllUnsplashPhotosLiveData() : LiveData<PagingData<UnsplashPhoto>> {
        val newResult: LiveData<PagingData<UnsplashPhoto>> =
            unsplashRepository.getPhotosLiveData().cachedIn(viewModelScope)
        unsplashPhotosLiveData.value = newResult.value
        return newResult
    }
}