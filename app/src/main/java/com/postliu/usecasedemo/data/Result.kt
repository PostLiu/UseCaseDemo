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

    companion object {

        inline fun <R> Result<R>.onLoading(action: () -> Unit): Result<R> {
            if (this is Loading) action()
            return this
        }

        inline fun <R> Result<R>.onError(action: (Throwable) -> Unit): Result<R> {
            if (this is Error) action(throwable)
            return this
        }

        inline fun <R> Result<R>.onSuccess(action: (R) -> Unit): Result<R> {
            if (this is Success) action(data)
            return this
        }

        inline fun <R, T> Result<R>.map(action: (R) -> T): Result<T> {
            return when (this) {
                is Loading -> Loading
                is Error -> Error(throwable)
                is Success -> Success(action(data))
            }
        }
    }
}

val Result<*>.succeeded
    get() = this is Result.Success && data != null