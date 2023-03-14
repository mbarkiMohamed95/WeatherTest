package com.example.data.utils


sealed class DataState<out R> {
    data class Success<out T>(val data: T) : DataState<T>()
    data class Error(val exception: Exception?=null, val state: Boolean = false) : DataState<Nothing>()
    object Idle : DataState<Nothing>()
}

