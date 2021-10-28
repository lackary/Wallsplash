package com.lacklab.app.wallsplash.util

import com.lacklab.app.wallsplash.data.api.ResultResponse
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResultCall<T>constructor(
    private val call: Call<T>
) : Call<ResultResponse<T>> {
    override fun clone(): Call<ResultResponse<T>> {
        TODO("Not yet implemented")
    }

    override fun execute(): Response<ResultResponse<T>> {
        TODO("Not yet implemented")
    }

    override fun enqueue(callback: Callback<ResultResponse<T>>) = call.enqueue(object: Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) {
            response.body()?.let {
                when(response.code()) {
                    in 200..299 -> {
                        callback.onResponse(this@ResultCall, Response.success(ResultResponse.Success(it, response.code())))
                    }
                    in 400..409 -> {
                        callback.onResponse(this@ResultCall, Response.success(ResultResponse.ApiError(response.message(), response.code())))
                    }
                }
            }?: callback.onResponse(this@ResultCall, Response.success(ResultResponse.NullResultResponse()))
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            TODO("Not yet implemented")
        }


    })

    override fun isExecuted(): Boolean {
        TODO("Not yet implemented")
    }

    override fun cancel() {
        TODO("Not yet implemented")
    }

    override fun isCanceled(): Boolean {
        TODO("Not yet implemented")
    }

    override fun request(): Request {
        TODO("Not yet implemented")
    }

    override fun timeout(): Timeout {
        TODO("Not yet implemented")
    }

}