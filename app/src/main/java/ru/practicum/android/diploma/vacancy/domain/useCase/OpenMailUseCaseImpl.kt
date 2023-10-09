package ru.practicum.android.diploma.vacancy.domain.useCase

import ru.practicum.android.diploma.vacancy.ui.navigator.ExternalNavigator

class OpenMailUseCaseImpl(private val externalNavigator: ExternalNavigator) : OpenMailUseCase {
    override fun execute(mailTo: String) {
        externalNavigator.openMail(mailTo)
    }
}