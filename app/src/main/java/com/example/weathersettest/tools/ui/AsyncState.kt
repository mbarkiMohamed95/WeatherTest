package com.example.weathersettest.tools.ui


sealed class AsyncState<out T : Any> {
    data class Success<out T : Any>(val data: T? = null) : AsyncState<T>()
    open class Failure(private val exception: Exception) : AsyncState<Nothing>()
    data class Loading<out T : Any>(val data: T? = null) : AsyncState<T>()
}
