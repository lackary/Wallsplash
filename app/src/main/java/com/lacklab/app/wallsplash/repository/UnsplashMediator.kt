package com.lacklab.app.wallsplash.repository

import androidx.lifecycle.LiveData
import androidx.paging.RemoteMediator
import com.lacklab.app.wallsplash.AppExecutors
import com.lacklab.app.wallsplash.api.ApiResponse
import com.lacklab.app.wallsplash.api.UnsplashService
import com.lacklab.app.wallsplash.data.UnsplashPhoto
import com.lacklab.app.wallsplash.data.UnsplashSearchPhotos
import com.lacklab.app.wallsplash.vo.Resource
import javax.inject.Inject

class UnsplashMediator constructor(
    private val appExecutors: AppExecutors,
    private val service: UnsplashService
) {
//    fun loadAllPhotos(page: Int, pageSize: Int  ): LiveData<Resource<List<UnsplashPhoto>>> {
//        return object : NetworkBoundResource<List<UnsplashPhoto>, List<UnsplashPhoto>>(appExecutors) {
//            override fun createCall() = service.getPhotos(page, pageSize)
//        }.asLiveData()
//    }
}