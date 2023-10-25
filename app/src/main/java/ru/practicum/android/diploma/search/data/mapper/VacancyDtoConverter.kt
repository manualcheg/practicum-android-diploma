package ru.practicum.android.diploma.search.data.mapper

import ru.practicum.android.diploma.common.domain.model.vacancy_models.AreaVacancy
import ru.practicum.android.diploma.common.domain.model.vacancy_models.Contacts
import ru.practicum.android.diploma.common.domain.model.vacancy_models.Employer
import ru.practicum.android.diploma.common.domain.model.vacancy_models.Employment
import ru.practicum.android.diploma.common.domain.model.vacancy_models.Experience
import ru.practicum.android.diploma.common.domain.model.vacancy_models.KeySkill
import ru.practicum.android.diploma.common.domain.model.vacancy_models.LogoUrls
import ru.practicum.android.diploma.common.domain.model.vacancy_models.Phone
import ru.practicum.android.diploma.common.domain.model.vacancy_models.Salary
import ru.practicum.android.diploma.common.domain.model.vacancy_models.Schedule
import ru.practicum.android.diploma.common.domain.model.vacancy_models.Vacancies
import ru.practicum.android.diploma.common.domain.model.vacancy_models.Vacancy
import ru.practicum.android.diploma.search.data.model.VacanciesSearchResponse
import ru.practicum.android.diploma.search.data.model.dto.AreaDto
import ru.practicum.android.diploma.search.data.model.dto.ContactsDto
import ru.practicum.android.diploma.search.data.model.dto.EmployerDto
import ru.practicum.android.diploma.search.data.model.dto.EmploymentDto
import ru.practicum.android.diploma.search.data.model.dto.ExperienceDto
import ru.practicum.android.diploma.search.data.model.dto.KeySkillDto
import ru.practicum.android.diploma.search.data.model.dto.LogoUrlsDto
import ru.practicum.android.diploma.search.data.model.dto.PhoneDto
import ru.practicum.android.diploma.search.data.model.dto.SalaryDto
import ru.practicum.android.diploma.search.data.model.dto.ScheduleDto
import ru.practicum.android.diploma.search.data.model.dto.VacancyDto
import ru.practicum.android.diploma.vacancy.data.model.VacancySearchResponse

class VacancyDtoConverter {

    fun mapVacanciesSearchResponseToVacancies(vacanciesSearchResponse: VacanciesSearchResponse): Vacancies {
        vacanciesSearchResponse.apply {
            return Vacancies(
                vacancyList = items.map { mapVacancyDtoToVacancy(it) },
                page = page,
                pages = pages,
                found = found
            )
        }
    }

    private fun mapVacancyDtoToVacancy(vacancyDto: VacancyDto): Vacancy {
        vacancyDto.apply {
            return Vacancy(
                id,
                name,
                area?.let { mapArea(it) },
                employer?.let { mapEmployer(it) },
                salary?.let { mapSalary(it) },
                experience?.let { mapExperience(it) },
                employment?.let { mapEmployment(it) },
                description,
                keySkills?.let { convertKeySkills(it) },
                schedule?.let { mapSchedule(it) },
                contacts?.let { mapContacts(it) }
            )
        }
    }

    fun mapVacancySearchResponseToVacancy(vacancyDto: VacancySearchResponse): Vacancy {
        vacancyDto.apply {
            return Vacancy(
                id,
                name,
                area?.let { mapArea(it) },
                employer?.let { mapEmployer(it) },
                salary?.let { mapSalary(it) },
                experience?.let { mapExperience(it) },
                employment?.let { mapEmployment(it) },
                description,
                keySkills?.let { convertKeySkills(it) },
                schedule?.let { mapSchedule(it) },
                contacts?.let { mapContacts(it) }
            )
        }
    }

    private fun mapEmployer(employerDto: EmployerDto): Employer {
        employerDto.apply {
            return Employer(
                id,
                logoUrls?.let { mapLogoUrls(it) },
                name,
            )
        }
    }

    private fun mapLogoUrls(logoUrlsDto: LogoUrlsDto): LogoUrls {
        return LogoUrls(
            logoUrlsDto.logo240,
            logoUrlsDto.logo90,
            logoUrlsDto.original
        )
    }

    private fun mapSalary(salaryDto: SalaryDto): Salary {
        return Salary(
            salaryDto.currency,
            salaryDto.from,
            salaryDto.gross,
            salaryDto.to
        )
    }

    private fun mapExperience(experienceDto: ExperienceDto): Experience {
        return Experience(
            experienceDto.id,
            experienceDto.name
        )
    }

    private fun mapEmployment(employmentDto: EmploymentDto): Employment {
        return Employment(
            employmentDto.id,
            employmentDto.name
        )
    }

    private fun mapKeySkill(keySkillDto: KeySkillDto): KeySkill {
        return KeySkill(
            keySkillDto.name
        )
    }

    private fun convertKeySkills(keySkillsDto: List<KeySkillDto>): List<KeySkill> {
        return keySkillsDto.map { keySkillDto -> mapKeySkill(keySkillDto) }
    }

    private fun mapSchedule(scheduleDto: ScheduleDto): Schedule {
        return Schedule(
            scheduleDto.id,
            scheduleDto.name
        )
    }

    private fun mapContacts(contactsDto: ContactsDto): Contacts {
        return Contacts(
            contactsDto.email,
            contactsDto.name,
            contactsDto.phones?.let {
                convertPhones(it)
            }
        )
    }

    private fun mapPhone(phoneDto: PhoneDto): Phone {
        return Phone(
            phoneDto.city,
            phoneDto.comment,
            phoneDto.country,
            phoneDto.number
        )
    }

    private fun mapArea(areaDto: AreaDto): AreaVacancy {
        return AreaVacancy(
            id = areaDto.id,
            name = areaDto.name
        )
    }

    private fun convertPhones(phonesDto: List<PhoneDto>): List<Phone> {
        return phonesDto.map { phoneDto -> mapPhone(phoneDto) }
    }
}