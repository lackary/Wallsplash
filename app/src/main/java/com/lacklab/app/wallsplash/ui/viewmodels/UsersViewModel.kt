package com.lacklab.app.wallsplash.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import com.lacklab.app.wallsplash.base.BaseViewModel
import com.lacklab.app.wallsplash.data.repo.UnsplashRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val unsplashRepository: UnsplashRepository
) : BaseViewModel() {
}