package ru.practicum.android.diploma.search.data.mapper

import ru.practicum.android.diploma.common.domain.models.Contacts
import ru.practicum.android.diploma.common.domain.models.Employer
import ru.practicum.android.diploma.common.domain.models.Employment
import ru.practicum.android.diploma.common.domain.models.Experience
import ru.practicum.android.diploma.common.domain.models.KeySkill
import ru.practicum.android.diploma.common.domain.models.LogoUrls
import ru.practicum.android.diploma.common.domain.models.Phone
import ru.practicum.android.diploma.common.domain.models.Salary
import ru.practicum.android.diploma.common.domain.models.Schedule
import ru.practicum.android.diploma.common.domain.models.Vacancy
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

class VacancyDbConverter {

    fun map(vacancyDto: VacancyDto): Vacancy {
        vacancyDto.apply {
            return Vacancy(
                name,
                employer?.let { map(it) },
                salary?.let { map(it) },
                experience?.let { map(it) },
                employment?.let { map(it) },
                description,
                convertKeySkills(keySkills),
                schedule?.let { map(it) },
                contacts?.let { map(it) }
            )
        }
    }


    private fun map(employerDto: EmployerDto): Employer {
        employerDto.apply {
            return Employer(
                alternateUrl,
                blacklisted,
                id,
                logoUrls?.let { map(it) },
                name,
                trusted,
                url
            )
        }
    }

    private fun map(logoUrlsDto: LogoUrlsDto): LogoUrls {
        return LogoUrls(
            logoUrlsDto.logo240,
            logoUrlsDto.logo90,
            logoUrlsDto.original
        )
    }

    private fun map(salaryDto: SalaryDto): Salary {
        return Salary(
            salaryDto.currency,
            salaryDto.from,
            salaryDto.gross,
            salaryDto.to
        )
    }

    private fun map(experienceDto: ExperienceDto): Experience {
        return Experience(
            experienceDto.id,
            experienceDto.name
        )
    }

    private fun map(employmentDto: EmploymentDto): Employment {
        return Employment(
            employmentDto.id,
            employmentDto.name
        )
    }

    private fun map(keySkillDto: KeySkillDto): KeySkill {
        return KeySkill(
            keySkillDto.name
        )
    }
    private fun convertKeySkills(keySkillsDto: List<KeySkillDto>): List<KeySkill> {
        return keySkillsDto.map { keySkillDto -> map(keySkillDto) }
    }

    private fun map(scheduleDto: ScheduleDto): Schedule {
        return Schedule(
            scheduleDto.id,
            scheduleDto.name
        )
    }

    private fun map(contactsDto: ContactsDto): Contacts {
        return Contacts(
            contactsDto.email,
            contactsDto.name,
            contactsDto.phones?.let {
                convertPhones(it)
            }
        )
    }

    private fun map(phoneDto: PhoneDto): Phone {
        return Phone(
            phoneDto.city,
            phoneDto.comment,
            phoneDto.country,
            phoneDto.number
        )
    }
    private fun convertPhones(phonesDto: List<PhoneDto>): List<Phone> {
        return phonesDto.map { phoneDto -> map(phoneDto) }
    }

}