package com.lacklab.app.wallsplash.data.repository

import androidx.annotation.MainThread

import com.lacklab.app.wallsplash.AppExecutors

abstract class UnsplashMediator<ResultType, RequestType>
@MainThread constructor(private val appExecutors: AppExecutors) {
}