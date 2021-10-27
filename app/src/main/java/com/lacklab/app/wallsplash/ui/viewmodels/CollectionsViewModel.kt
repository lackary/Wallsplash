package com.lacklab.app.wallsplash.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.lacklab.app.wallsplash.data.UnsplashCollection
import com.lacklab.app.wallsplash.repository.UnsplashRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CollectionsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val unsplashRepository: UnsplashRepository
) : ViewModel() {
    private var unsplashCollections = MutableLiveData<PagingData<UnsplashCollection>>()

    suspend fun getCollectionsLiveData(): LiveData<PagingData<UnsplashCollection>> {
        val newResult: LiveData<PagingData<UnsplashCollection>> =
            unsplashRepository.getCollectionLiveData()
        unsplashCollections.value = newResult.value
        return newResult
    }
}