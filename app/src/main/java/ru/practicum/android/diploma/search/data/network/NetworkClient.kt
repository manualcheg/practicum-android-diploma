package ru.practicum.android.diploma.search.data.network

// Интерфейс предполагает, что некоторому сетевому клиенту будут переданы параметры запроса
// в виде экземпляра класса из пакета dto
// после чего клиент должен вернуть какой-то ответ.
interface NetworkClient {
    suspend fun doRequest(dto: Any): Response
}