package ru.practicum.android.diploma.vacancy.domain.useCase

import ru.practicum.android.diploma.vacancy.ui.navigator.ExternalNavigator

class ShareVacancyByIdUseCaseImpl(private val externalNavigator: ExternalNavigator) :
    ShareVacancyByIdUseCase {
    override fun execute(id: Int) {
        externalNavigator.shareVacancyById(id)
    }
}