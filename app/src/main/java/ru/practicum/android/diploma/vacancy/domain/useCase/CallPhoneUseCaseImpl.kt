package ru.practicum.android.diploma.vacancy.domain.useCase

import ru.practicum.android.diploma.vacancy.domain.repository.ExternalNavigator

class CallPhoneUseCaseImpl(private val externalNavigator: ExternalNavigator) : CallPhoneUseCase {
    override fun execute(phoneNumber: String) {
        externalNavigator.callPhone(phoneNumber)
    }
}