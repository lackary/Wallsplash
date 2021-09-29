package com.lacklab.app.wallsplash.api

import androidx.lifecycle.LiveData
import com.lacklab.app.wallsplash.BuildConfig
import com.lacklab.app.wallsplash.data.UnsplashPhoto
import com.lacklab.app.wallsplash.data.UnsplashSearchPhotos
import com.lacklab.app.wallsplash.factory.DataCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface UnsplashService {
    @GET("photos")
    suspend fun getPhotos(
        @Query("page") page: Int,
        @Query("per_page") prePage: Int = 10,
        @Header("Authorization")
        clientId: String = "Client-ID " + BuildConfig.UNSPLASH_ACCESS_KEY,
    ) : ApiResponse<List<UnsplashPhoto>>

    @GET("search/photos")
    suspend fun searchPhotos(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Header("Authorization")
        clientId: String = "Client-ID " + BuildConfig.UNSPLASH_ACCESS_KEY,
    ) : UnsplashSearchPhotos

    companion object {
        private const val BASE_URL = "https://api.unsplash.com/"

        fun create(): UnsplashService {
            val logger = HttpLoggingInterceptor().apply { level = Level.BASIC }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(DataCallAdapterFactory())
                .build()
                .create(UnsplashService::class.java)
        }
    }
}