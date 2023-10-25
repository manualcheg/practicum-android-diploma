package ru.practicum.android.diploma.vacancy.domain.useCase

interface OpenMailUseCase {
    fun execute(mailTo: String)
}