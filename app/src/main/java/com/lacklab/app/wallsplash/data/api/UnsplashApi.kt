package com.lacklab.app.wallsplash.data.api

import com.lacklab.app.wallsplash.BuildConfig
import com.lacklab.app.wallsplash.data.model.*
import com.lacklab.app.wallsplash.factory.DataCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface UnsplashApi {

    @GET("photos")
    suspend fun getPhotos(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 10,
        @Header("Authorization")
        clientId: String = "Client-ID " + BuildConfig.UNSPLASH_ACCESS_KEY,
    ) : ApiResponse<List<UnsplashPhoto>>

    @GET("photos/{photo_id}")
    suspend fun getPhoto(
        @Path(value = "photo_id", encoded = true) photoId: String,
        @Header("Authorization")
        clientId: String = "Client-ID " + BuildConfig.UNSPLASH_ACCESS_KEY,
    ) : ApiResponse<UnsplashPhoto>

    @GET("collections")
    suspend fun getCollections(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 10,
        @Header("Authorization")
        clientId: String = "Client-ID " + BuildConfig.UNSPLASH_ACCESS_KEY,
    ) : ApiResponse<List<UnsplashCollection>>

    @GET("collections/{collection_id}")
    suspend fun getCollection(
        @Path(value = "collection_id", encoded = true) collectionId: String,
        @Header("Authorization")
        clientId: String = "Client-ID " + BuildConfig.UNSPLASH_ACCESS_KEY,
    ) : ApiResponse<UnsplashCollection>

    @GET("collections/{collection_id}/photos")
    suspend fun getCollectionPhoto(
        @Path(value = "collection_id", encoded = true) collectionId: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
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
    ) : ApiResponse<UnsplashSearchPhotos>

    @GET("search/collections")
    suspend fun searchCollections(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Header("Authorization")
        clientId: String = "Client-ID " + BuildConfig.UNSPLASH_ACCESS_KEY,
    ) : ApiResponse<UnsplashSearchCollections>

    @GET("search/users")
    suspend fun searchUsers(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Header("Authorization")
        clientId: String = "Client-ID " + BuildConfig.UNSPLASH_ACCESS_KEY,
    ) : ApiResponse<UnsplashSearchUsers>

    companion object {
        private const val BASE_URL = "https://api.unsplash.com/"

        fun create(): UnsplashApi {
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
                .create(UnsplashApi::class.java)
        }
    }
}