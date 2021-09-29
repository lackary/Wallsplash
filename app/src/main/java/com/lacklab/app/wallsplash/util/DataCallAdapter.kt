package com.lacklab.app.wallsplash.util

import androidx.lifecycle.LiveData
import com.lacklab.app.wallsplash.api.ApiResponse
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import java.util.concurrent.atomic.AtomicBoolean

class DataCallAdapter<R>(private val responseType: Type) :
    CallAdapter<R, Call<ApiResponse<R>>> {

    override fun responseType() = responseType

    override fun adapt(call: Call<R>): Call<ApiResponse<R>> = DataCall(call)
}