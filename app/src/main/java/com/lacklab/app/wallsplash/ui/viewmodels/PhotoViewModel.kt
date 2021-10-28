package com.lacklab.app.wallsplash.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.lacklab.app.wallsplash.data.repository.UnsplashRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PhotoViewModel@Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val unsplashRepository: UnsplashRepository
) : ViewModel()  {
}