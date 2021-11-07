package com.lacklab.app.wallsplash.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.lacklab.app.wallsplash.base.BaseViewModel
import com.lacklab.app.wallsplash.data.model.UnsplashCollection
import com.lacklab.app.wallsplash.data.model.UnsplashPhoto
import com.lacklab.app.wallsplash.data.repository.UnsplashRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val unsplashRepository: UnsplashRepository
) : BaseViewModel() {
    private var currentSearchPhotos: Flow<PagingData<UnsplashPhoto>>? = null
    private var currentSearchCollection: Flow<PagingData<UnsplashCollection>>? = null
    val queryString: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    fun searchPhotos(queryString:String): Flow<PagingData<UnsplashPhoto>> {
        val newResult: Flow<PagingData<UnsplashPhoto>> =
            unsplashRepository.getSearchPhotosStream(queryString).cachedIn(viewModelScope)
        currentSearchPhotos = newResult
        return newResult
    }

    fun searchCollections(queryString: String): Flow<PagingData<UnsplashCollection>>{
        val newResult: Flow<PagingData<UnsplashCollection>> =
            unsplashRepository.getSearchCollectionsStream(queryString).cachedIn(viewModelScope)
        currentSearchCollection = newResult
        return newResult
    }


}