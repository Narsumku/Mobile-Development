package com.bangkit.narsumku.data

sealed class Results<out T> {
    data object Loading : Results<Nothing>()
    data class Success<out T>(val data: T) : Results<T>()
    data class Error(val error: String) : Results<Nothing>()
}