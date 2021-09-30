package com.lacklab.app.wallsplash.repository

import androidx.annotation.MainThread

import com.lacklab.app.wallsplash.AppExecutors
import com.lacklab.app.wallsplash.api.UnsplashService

abstract class UnsplashMediator<ResultType, RequestType>
@MainThread constructor(private val appExecutors: AppExecutors) {
}