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
import ru.practicum.android.diploma.vacancy.data.db.ContactsEntity
import ru.practicum.android.diploma.vacancy.data.db.EmployerEntity
import ru.practicum.android.diploma.vacancy.data.db.EmploymentEntity
import ru.practicum.android.diploma.vacancy.data.db.ExperienceEntity
import ru.practicum.android.diploma.vacancy.data.db.FavoritesVacancyEntity
import ru.practicum.android.diploma.vacancy.data.db.KeySkillEntity
import ru.practicum.android.diploma.vacancy.data.db.LogoUrlsEntity
import ru.practicum.android.diploma.vacancy.data.db.PhoneEntity
import ru.practicum.android.diploma.vacancy.data.db.SalaryEntity
import ru.practicum.android.diploma.vacancy.data.db.ScheduleEntity
import java.util.Calendar

class VacancyDbConverter {

    fun map(vacancyEntity: FavoritesVacancyEntity): Vacancy {
        vacancyEntity.apply {
            return Vacancy(
                id,
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

    fun map(vacancy: Vacancy): FavoritesVacancyEntity {
        vacancy.apply {
            return FavoritesVacancyEntity(
                id,
                name,
                employer?.let { map(it) },
                salary?.let { map(it) },
                experience?.let { map(it) },
                employment?.let { map(it) },
                description,
                convertKeySkills(keySkills),
                schedule?.let { map(it) },
                contacts?.let { map(it) },
                createdAt = Calendar.getInstance().timeInMillis
            )
        }
    }

    private fun map(employerEntity: EmployerEntity): Employer {
        employerEntity.apply {
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

    private fun map(employer: Employer): EmployerEntity {
        employer.apply {
            return EmployerEntity(
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

    private fun map(logoUrlsEntity: LogoUrlsEntity): LogoUrls {
        return LogoUrls(
            logoUrlsEntity.logo240,
            logoUrlsEntity.logo90,
            logoUrlsEntity.original
        )
    }

    private fun map(logoUrls: LogoUrls): LogoUrlsEntity {
        return LogoUrlsEntity(
            logoUrls.logo240,
            logoUrls.logo90,
            logoUrls.original
        )
    }

    private fun map(salaryEntity: SalaryEntity): Salary {
        return Salary(
            salaryEntity.currency,
            salaryEntity.from,
            salaryEntity.gross,
            salaryEntity.to
        )
    }

    private fun map(salary: Salary): SalaryEntity {
        return SalaryEntity(
            salary.currency,
            salary.from,
            salary.gross,
            salary.to
        )
    }

    private fun map(experienceEntity: ExperienceEntity): Experience {
        return Experience(
            experienceEntity.id,
            experienceEntity.name
        )
    }

    private fun map(experience: Experience): ExperienceEntity {
        return ExperienceEntity(
            experience.id,
            experience.name
        )
    }

    private fun map(employmentEntity: EmploymentEntity): Employment {
        return Employment(
            employmentEntity.id,
            employmentEntity.name
        )
    }

    private fun map(employment: Employment): EmploymentEntity {
        return EmploymentEntity(
            employment.id,
            employment.name
        )
    }

    private fun map(keySkillEntity: KeySkillEntity): KeySkill {
        return KeySkill(
            keySkillEntity.name
        )
    }

    private fun map(keySkill: KeySkill): KeySkillEntity {
        return KeySkillEntity(
            keySkill.name
        )
    }

    private fun convertKeySkills(keySkillsEntity: List<KeySkillEntity>): List<KeySkill> {
        return keySkillsEntity.map { keySkillEntity -> map(keySkillEntity) }
    }

    private fun convertKeySkills(keySkills: List<KeySkill>): List<KeySkillEntity> {
        return keySkills.map { keySkill -> map(keySkill) }
    }

    private fun map(scheduleEntity: ScheduleEntity): Schedule {
        return Schedule(
            scheduleEntity.id,
            scheduleEntity.name
        )
    }

    private fun map(schedule: Schedule): ScheduleEntity {
        return ScheduleEntity(
            schedule.id,
            schedule.name
        )
    }

    private fun map(contactsEntity: ContactsEntity): Contacts {
        return Contacts(
            contactsEntity.email,
            contactsEntity.name,
            contactsEntity.phones?.let {
                convertPhones(it)
            }
        )
    }

    private fun map(contacts: Contacts): ContactsEntity {
        return ContactsEntity(
            contacts.email,
            contacts.name,
            contacts.phones?.let {
                convertPhones(it)
            }
        )
    }

    private fun map(phoneEntity: PhoneEntity): Phone {
        return Phone(
            phoneEntity.city,
            phoneEntity.comment,
            phoneEntity.country,
            phoneEntity.number
        )
    }

    private fun map(phone: Phone): PhoneEntity {
        return PhoneEntity(
            phone.city,
            phone.comment,
            phone.country,
            phone.number
        )
    }

    private fun convertPhones(phonesEntity: List<PhoneEntity>): List<Phone> {
        return phonesEntity.map { phoneEntity -> map(phoneEntity) }
    }

    private fun convertPhones(phones: List<Phone>): List<PhoneEntity> {
        return phones.map { phone -> map(phone) }
    }

}