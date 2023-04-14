package com.lacklab.app.wallsplash.ui.view.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.lacklab.app.wallsplash.base.BaseViewModel
import com.lacklab.app.wallsplash.util.UnsplashItem
import com.lacklab.app.wallsplash.data.repo.UnsplashRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val unsplashRepository: UnsplashRepository
) : BaseViewModel() {
    private var currentSearchPhotos: Flow<PagingData<UnsplashItem>>? = null
    private var currentSearchCollection: Flow<PagingData<UnsplashItem>>? = null
    val queryString: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    fun searchPhotos(queryString:String): Flow<PagingData<UnsplashItem>> {
        val newResult: Flow<PagingData<UnsplashItem>> =
            unsplashRepository.getSearchPhotosStream(queryString).cachedIn(viewModelScope)
        currentSearchPhotos = newResult
        return newResult
    }

    fun searchCollections(queryString: String): Flow<PagingData<UnsplashItem>>{
        val newResult: Flow<PagingData<UnsplashItem>> =
            unsplashRepository.getSearchCollectionsStream(queryString).cachedIn(viewModelScope)
        currentSearchCollection = newResult
        return newResult
    }
}