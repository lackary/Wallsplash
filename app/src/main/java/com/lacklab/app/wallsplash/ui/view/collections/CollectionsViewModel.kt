package com.lacklab.app.wallsplash.ui.view.collections

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.lacklab.app.wallsplash.base.BaseViewModel
import com.lacklab.app.wallsplash.util.UnsplashItem
import com.lacklab.app.wallsplash.data.repo.UnsplashRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class CollectionsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val unsplashRepository: UnsplashRepository
) : BaseViewModel() {

    private lateinit var _itemsFlow: Flow<PagingData<UnsplashItem>>
    val itemsFlow: Flow<PagingData<UnsplashItem>>
        get() = _itemsFlow

    init {
        getCollections()
    }

    private fun getCollections() {
        _itemsFlow = unsplashRepository.getCollections().cachedIn(viewModelScope)
    }

//    private fun getCollections() = launchPaging({
//        unsplashRepository.getCollectionStream().cachedIn(viewModelScope)
//    },{
//        _collectionsFlow = it
//    })

}