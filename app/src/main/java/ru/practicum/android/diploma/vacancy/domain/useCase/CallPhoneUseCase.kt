package ru.practicum.android.diploma.vacancy.domain.useCase

interface CallPhoneUseCase {
    fun execute(phoneNumber: String)
}