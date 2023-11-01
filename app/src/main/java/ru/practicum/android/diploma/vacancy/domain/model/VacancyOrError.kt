package ru.practicum.android.diploma.vacancy.domain.model

import ru.practicum.android.diploma.common.domain.model.vacancy_models.Vacancy
import ru.practicum.android.diploma.search.domain.model.ErrorStatusDomain

class VacancyOrError(val vacancy: Vacancy?, val error: ErrorStatusDomain?)