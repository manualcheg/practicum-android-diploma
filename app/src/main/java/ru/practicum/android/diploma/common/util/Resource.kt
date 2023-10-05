package ru.practicum.android.diploma.common.util

import ru.practicum.android.diploma.search.data.model.ErrorRemoteDataSource

sealed class Resource<T>(val data: T? = null, val errorStatus: ErrorRemoteDataSource? = null) {
    class Success<T>(data: T) : Resource<T>(data = data)
    class Error<T>(errorStatus: ErrorRemoteDataSource) : Resource<T>(errorStatus = errorStatus)
}