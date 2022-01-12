package com.lacklab.app.wallsplash.ui.view.photos

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
class PhotosViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val unsplashRepository: UnsplashRepository
) : BaseViewModel() {

    private lateinit var _itemsFlow: Flow<PagingData<UnsplashItem>>
    val itemsFlow: Flow<PagingData<UnsplashItem>>
        get() = _itemsFlow

    init {
        getPhotos()
    }

    private fun getPhotos() {
        _itemsFlow = unsplashRepository.getPhotos().cachedIn(viewModelScope)
    }

//    private fun getPhotos() = launchPaging({
//        unsplashRepository.getPhotosStream().cachedIn(viewModelScope)
//    }, {
//        _photoFlow = it
//    })

}