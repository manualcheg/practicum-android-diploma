package ru.practicum.android.diploma.search.domain.repository

interface NetworkConnectionProvider {
    fun isConnected(): Boolean
}