package com.lacklab.app.wallsplash.data.repo

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.lacklab.app.wallsplash.AppExecutors
import com.lacklab.app.wallsplash.data.api.ApiEmptyResponse
import com.lacklab.app.wallsplash.data.api.ApiErrorResponse
import com.lacklab.app.wallsplash.data.api.ApiResponse
import com.lacklab.app.wallsplash.data.api.ApiSuccessResponse
import com.lacklab.app.wallsplash.vo.Resource

/**
 * a generic class that can be provide a resource backed by both the sqlite and the network
 *
 * @param <ResultType> Type for the Resource data.
 * @param <RequestType> Type for the API Response.
 *
 * */
abstract class NetworkBoundResource<ResultType, RequestType>
@MainThread constructor(private val appExecutors: AppExecutors){

    private val result = MediatorLiveData<Resource<ResultType>>()

    init {
        result.value = Resource.loading(null)

        @Suppress("LeakingThis")
        fetchFromNetwork(/*dbSource*/)
    }

    @MainThread
    private fun setValue(newValue: Resource<ResultType>) {
        if (result.value != newValue) {
            result.value = newValue
        }
    }

    private fun fetchFromNetwork(/*dbSource: LiveData<ResultType>*/) {
        val apiResponse = createCall()

        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)
//            result.removeSource(dbSource)
            when (response) {
                is ApiSuccessResponse -> {
                }
                is ApiEmptyResponse -> {
                }
                is ApiErrorResponse -> {
                }
            }
        }
    }

    protected open fun onFetchFailed() {}

    fun asLiveData() = result as LiveData<Resource<ResultType>>

//    @WorkerThread
    protected open fun processResponse(response: ApiSuccessResponse<RequestType>) = response.body
//
//    @WorkerThread
//    protected abstract fun saveCallResult(item: RequestType)
//
//    @MainThread
//    protected abstract fun shouldFetch(data: ResultType?): Boolean
//
//    @MainThread
//    protected abstract fun loadFromDb(): LiveData<ResultType>

    @MainThread
    protected abstract fun createCall(): LiveData<ApiResponse<RequestType>>
}