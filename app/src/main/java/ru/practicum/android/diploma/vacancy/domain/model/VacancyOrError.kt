package ru.practicum.android.diploma.vacancy.domain.model

import ru.practicum.android.diploma.common.ui.model.VacancyUi
import ru.practicum.android.diploma.search.domain.model.ErrorStatusDomain

class VacancyOrError(val vacancy: VacancyUi?, val error: ErrorStatusDomain?) {
}