package com.lacklab.app.wallsplash.ui.view.gallery

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.lacklab.app.wallsplash.base.BaseViewModel
import com.lacklab.app.wallsplash.data.model.UnsplashPhoto
import com.lacklab.app.wallsplash.data.repo.UnsplashRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val unsplashRepository: UnsplashRepository
) : BaseViewModel() {

}