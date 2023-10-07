package ru.practicum.android.diploma.favorites.data.mapper

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.practicum.android.diploma.common.domain.model.vacancy_models.Area
import ru.practicum.android.diploma.common.domain.model.vacancy_models.Contacts
import ru.practicum.android.diploma.common.domain.model.vacancy_models.Employer
import ru.practicum.android.diploma.common.domain.model.vacancy_models.Employment
import ru.practicum.android.diploma.common.domain.model.vacancy_models.Experience
import ru.practicum.android.diploma.common.domain.model.vacancy_models.KeySkill
/*import ru.practicum.android.diploma.common.domain.model.vacancy_models.LogoUrls
import ru.practicum.android.diploma.common.domain.model.vacancy_models.Phone*/
import ru.practicum.android.diploma.common.domain.model.vacancy_models.Salary
import ru.practicum.android.diploma.common.domain.model.vacancy_models.Schedule
import ru.practicum.android.diploma.common.domain.model.vacancy_models.Vacancy
/*import ru.practicum.android.diploma.favorites.data.db.AreaEntity
import ru.practicum.android.diploma.favorites.data.db.ContactsEntity
import ru.practicum.android.diploma.favorites.data.db.EmployerEntity
import ru.practicum.android.diploma.favorites.data.db.EmploymentEntity
import ru.practicum.android.diploma.favorites.data.db.ExperienceEntity*/
import ru.practicum.android.diploma.favorites.data.db.FavoritesVacancyEntity
/*import ru.practicum.android.diploma.favorites.data.db.KeySkillEntity
import ru.practicum.android.diploma.favorites.data.db.LogoUrlsEntity
import ru.practicum.android.diploma.favorites.data.db.PhoneEntity
import ru.practicum.android.diploma.favorites.data.db.SalaryEntity
import ru.practicum.android.diploma.favorites.data.db.ScheduleEntity*/
import java.util.Calendar

class VacancyDbConverter {

/*    fun map(vacancyEntity: FavoritesVacancyEntity): Vacancy {
        vacancyEntity.apply {
            return Vacancy(
                id,
                name,
                area?.let { map(it) },
                employer?.let { map(it) },
                salary?.let { map(it) },
                experience?.let { map(it) },
                employment?.let { map(it) },
                description,
                keySkills?.let { convertKeySkillsEntityToKeySkill(it) },
                schedule?.let { map(it) },
                contacts?.let { map(it) },
            )
        }
    }

    fun map(vacancy: Vacancy): FavoritesVacancyEntity {
        vacancy.apply {
            return FavoritesVacancyEntity(
                id,
                name,
                area?.let { map(it) },
                employer?.let { map(it) },
                salary?.let { map(it) },
                experience?.let { map(it) },
                employment?.let { map(it) },
                description,
                keySkills?.let { convertKeySkillsToKeySkillEntity(it) },
                schedule?.let { map(it) },
                contacts?.let { map(it) },
                createdAt = Calendar.getInstance().timeInMillis
            )
        }
    }*/

    fun map(vacancyEntity: FavoritesVacancyEntity): Vacancy {
        vacancyEntity.apply {
            return Vacancy(
                id = id,
                name = name,
                area = Gson().fromJson(area, object : TypeToken<Area>() {}.type),
                employer = Gson().fromJson(employer, object : TypeToken<Employer>() {}.type),
                salary = Gson().fromJson(salary, object : TypeToken<Salary>() {}.type),
                experience = Gson().fromJson(experience, object : TypeToken<Experience>() {}.type),
                employment = Gson().fromJson(employment, object : TypeToken<Employment>() {}.type),
                description = description,
                keySkills = Gson().fromJson(keySkills, object : TypeToken<List<KeySkill>>() {}.type),
                schedule = Gson().fromJson(schedule, object : TypeToken<Schedule>() {}.type),
                contacts = Gson().fromJson(contacts, object : TypeToken<Contacts>() {}.type),
            )
        }
    }

    fun map(vacancy: Vacancy): FavoritesVacancyEntity {
        vacancy.apply {
            return FavoritesVacancyEntity(
                id = id,
                name = name,
                area = Gson().toJson(area),
                employer = Gson().toJson(employer),
                salary = Gson().toJson(salary),
                experience = Gson().toJson(experience),
                employment = Gson().toJson(employment),
                description = description,
                keySkills = Gson().toJson(keySkills),
                schedule = Gson().toJson(schedule),
                contacts = Gson().toJson(contacts),
                createdAt = Calendar.getInstance().timeInMillis
            )
        }
    }

/*    private fun convertKeySkillsEntityToKeySkill(keySkillsEntity: List<KeySkillEntity>): List<KeySkill> {
        return keySkillsEntity.map { keySkillEntity -> map(keySkillEntity) }
    }

    private fun map(keySkillEntity: KeySkillEntity): KeySkill {
        return KeySkill(
            keySkillEntity.name
        )
    }

    private fun map(employerEntity: EmployerEntity): Employer {
        employerEntity.apply {
            return Employer(
                id,
                logoUrls?.let { map(it) },
                name,
            )
        }
    }

    private fun map(employer: Employer): EmployerEntity {
        employer.apply {
            return EmployerEntity(
                id,
                logoUrls?.let { map(it) },
                name
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



    private fun map(keySkill: KeySkill): KeySkillEntity {
        return KeySkillEntity(
            keySkill.name
        )
    }

    private fun convertKeySkillsToKeySkillEntity(keySkills: List<KeySkill>): List<KeySkillEntity> {
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
                convertPhoneEntityToPhone(it)
            }
        )
    }

    private fun map(contacts: Contacts): ContactsEntity {
        return ContactsEntity(
            contacts.email,
            contacts.name,
            contacts.phones?.let {
                convertPhonesToPhoneEntity(it)
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

    private fun map(area: Area): AreaEntity {
        return AreaEntity(
            area.id,
            area.name
        )
    }

    private fun map(areaEntity: AreaEntity): Area {
        return Area(
            areaEntity.id,
            areaEntity.name
        )
    }

    private fun convertPhoneEntityToPhone(phonesEntity: List<PhoneEntity>): List<Phone> {
        return phonesEntity.map { phoneEntity -> map(phoneEntity) }
    }

    private fun convertPhonesToPhoneEntity(phones: List<Phone>): List<PhoneEntity> {
        return phones.map { phone -> map(phone) }
    }*/

}