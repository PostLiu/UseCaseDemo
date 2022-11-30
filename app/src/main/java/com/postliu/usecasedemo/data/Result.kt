package com.postliu.usecasedemo.data

sealed class Result<out R> {

    object Loading : Result<Nothing>()

    data class Error(val throwable: Throwable) : Result<Nothing>()

    data class Success<out T>(val data: T) : Result<T>()

    override fun toString(): String {
        return when (this) {
            is Loading -> "Loading"
            is Error -> "Error[throwable=$throwable]"
            is Success<*> -> "Success[data=$data]"
        }
    }
}

val Result<*>.succeeded
    get() = this is Result.Success && data != null