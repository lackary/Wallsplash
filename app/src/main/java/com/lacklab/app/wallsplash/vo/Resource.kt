package com.lacklab.app.wallsplash.vo

import com.lacklab.app.wallsplash.util.enums.Status
import com.lacklab.app.wallsplash.util.enums.Status.SUCCESS
import com.lacklab.app.wallsplash.util.enums.Status.ERROR
import com.lacklab.app.wallsplash.util.enums.Status.LOADING


/**
 * a generic class that describes data with a status
 * @param <T>
 * */
data class Resource<out T> constructor(val status: Status, val data: T?, val message: String?){
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(ERROR, data, msg)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(LOADING, data, null)
        }
    }
}