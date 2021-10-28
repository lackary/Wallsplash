package com.lacklab.app.wallsplash.di

import com.lacklab.app.wallsplash.data.api.UnsplashApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideUnsplashService():UnsplashApi {
        return UnsplashApi.create()
    }
}