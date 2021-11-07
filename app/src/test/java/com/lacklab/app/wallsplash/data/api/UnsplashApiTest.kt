package com.lacklab.app.wallsplash.data.api

import com.google.common.truth.Truth.assertThat
import com.lacklab.app.wallsplash.factory.DataCallAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.sql.Time

const val BASE_URL = "https://api.unsplash.com/"

@RunWith(JUnit4::class)
class UnsplashApiTest {
    private var unsplashApi: UnsplashApi? = null
    private var mockWebServer: MockWebServer? = null

    @Before
    fun startService() {
        mockWebServer = MockWebServer()
        unsplashApi = Retrofit.Builder()
            .baseUrl(mockWebServer!!.url(BASE_URL))
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(DataCallAdapterFactory())
            .build()
            .create(UnsplashApi::class.java)
    }

    @After
    fun stopService() {
        mockWebServer?.shutdown()
    }

    @Test
    fun getPhotosTest(){
        runBlocking {
            launch(Dispatchers.Default) {
                try {
                    val response = unsplashApi?.getPhotos(1, 10)
                    when(response){
                        is ApiSuccessResponse -> {
                            assertThat(response).isNotNull()
                            Assert.assertEquals(10, response.body.size)
                               Timber.d("body: ${response.body}")
                        }
                        is ApiErrorResponse -> {
                            throw Exception(response.errorMessage)
                        }
                        is ApiEmptyResponse -> {
                            Timber.d("ApiEmptyResponse")
                        }
                    }
                } catch (ex: Exception) {
                    Timber.d("ex: ${ex.message}")
                }

            }
        }
    }
}