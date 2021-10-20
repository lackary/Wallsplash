package com.lacklab.app.wallsplash.dao

import androidx.room.*
import com.lacklab.app.wallsplash.data.UnsplashPhoto

@Dao
interface UnsplashDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhotos(photos: List<UnsplashPhoto>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhotos(photo: UnsplashPhoto)

    @Delete
    suspend fun deletePhotos(photo: UnsplashPhoto)

    @Update
    suspend fun updatePhoto(photo: UnsplashPhoto)
}