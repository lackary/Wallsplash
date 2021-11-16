package com.lacklab.app.wallsplash.ui.viewmodels

import androidx.lifecycle.*
import com.lacklab.app.wallsplash.base.BaseViewModel
import com.lacklab.app.wallsplash.data.model.UnsplashPhoto
import com.lacklab.app.wallsplash.data.repo.UnsplashRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val unsplashRepository: UnsplashRepository
) : BaseViewModel()  {
//    val photoId: String = savedStateHandle["id"] ?:
//        throw IllegalArgumentException("missing user id")
    private val _photo = MutableLiveData<UnsplashPhoto>()
    val photo: LiveData<UnsplashPhoto> = _photo
//    init {
//        getPhoto(photoId)
//    }
    private fun getPhoto(id: String) {
        viewModelScope.launch {
            _photo.value = unsplashRepository.getPhoto(id)
        }

    }
}