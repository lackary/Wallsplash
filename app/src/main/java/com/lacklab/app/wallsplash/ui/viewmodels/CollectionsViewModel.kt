package com.lacklab.app.wallsplash.ui.viewmodels

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.lacklab.app.wallsplash.base.BaseViewModel
import com.lacklab.app.wallsplash.data.model.UnsplashCollection
import com.lacklab.app.wallsplash.data.repository.UnsplashRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val unsplashRepository: UnsplashRepository
) : BaseViewModel() {
    private var unsplashCollections = MutableLiveData<PagingData<UnsplashCollection>>()

    private lateinit var _collectionsFlow: Flow<PagingData<UnsplashCollection>>
    val collectionFlow: Flow<PagingData<UnsplashCollection>>
        get() = _collectionsFlow

    init {
        getCollections()
    }
    private fun getCollections() = launchPaging({
        unsplashRepository.getCollectionStream().cachedIn(viewModelScope)
    },{
        _collectionsFlow = it
    })
//    private fun getCollections() = viewModelScope.launch {
//        val result = unsplashRepository.getCollectionStream().cachedIn(viewModelScope)
//        _collectionsFlow = result
//    }

    suspend fun getCollectionsLiveData(): LiveData<PagingData<UnsplashCollection>> {
        val newResult: LiveData<PagingData<UnsplashCollection>> =
            unsplashRepository.getCollectionLiveData()
        unsplashCollections.value = newResult.value
        return newResult
    }
}