package com.lacklab.app.wallsplash.ui.view.photo

import androidx.lifecycle.*
import com.lacklab.app.wallsplash.base.BaseViewModel
import com.lacklab.app.wallsplash.data.model.UnsplashCollection
import com.lacklab.app.wallsplash.data.model.UnsplashData
import com.lacklab.app.wallsplash.data.model.UnsplashPhoto
import com.lacklab.app.wallsplash.data.repo.UnsplashRepository
import com.lacklab.app.wallsplash.util.UiState
import com.lacklab.app.wallsplash.vo.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PhotoViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val unsplashRepository: UnsplashRepository
) : BaseViewModel()  {
//    val photoId: String = savedStateHandle["id"] ?:
//        throw IllegalArgumentException("missing user id")
    private val _photo = MutableLiveData<UnsplashPhoto>()
    val photo: LiveData<UnsplashPhoto>
        get() = _photo
    private val _collection = MutableLiveData<UnsplashCollection>()
    val collection: LiveData<UnsplashCollection>
        get() = _collection
    private val _result = MutableLiveData<List<UnsplashData>>()
    val result: LiveData<List<UnsplashData>>
        get() = _result

    private val _uiStates = MutableStateFlow<UiState<UnsplashPhoto>>(UiState.Success(null))
    val uiState: StateFlow<UiState<UnsplashPhoto>> = _uiStates

    init {
        getPhoto("z3htkdHUh5w", "8961198")
    }
    private fun getPhoto(photoId: String, collectionId: String) {
        viewModelScope.launch {
            Timber.d("getPhoto")
            val b = async {
                Timber.d("async getCollection")
                unsplashRepository.getCollection(collectionId) }
//            val data = unsplashRepository.getCollection(collectionId)
            unsplashRepository.getPhoto(photoId).collect {
                when(it) {
                    is UiState.Loading -> Timber.d("LOADING")
                    is UiState.Success -> Timber.d("SUCCESS")
                    is UiState.Error -> Timber.d("Error")
                }
                _uiStates.value = it
            }
//            val a = async { unsplashRepository.getPhoto(photoId) }
//            _photo.value = a.await()
            val data = b.await()
            val unsplashData = UnsplashData(data, type = 3)
            val temp = mutableListOf<UnsplashData>()
            temp.add(unsplashData)
            for (item in temp) {
                if(item.type == 3) {
                    val data = item.value as UnsplashCollection
                    Timber.d("data: ${data.totalPhotos}, item type: ${item.type}")
                }
            }
//            todoA()
//            todoB()
        }
    }

    private suspend fun todoA() {
        Timber.d("todoA")
        for( i in 0..100) {
            delay(100L)
            Timber.d("todoA: $i")
        }
    }

    private suspend fun todoB() {
        Timber.d("todoB")
        for( i in 0..100) {
            delay(100L)
            Timber.d("todoB: $i")
        }
    }
}