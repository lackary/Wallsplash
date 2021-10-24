package com.lacklab.app.wallsplash.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.lacklab.app.wallsplash.repository.UnsplashRepository

class UsersViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val unsplashRepository: UnsplashRepository
) : ViewModel() {
}