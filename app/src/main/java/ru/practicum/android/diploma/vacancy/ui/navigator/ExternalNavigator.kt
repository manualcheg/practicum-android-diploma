package ru.practicum.android.diploma.vacancy.ui.navigator

interface ExternalNavigator {
    fun openMail(mailTo: String)
    fun callPhone(phoneNumber: String)
    fun shareVacancyById(id: Int)
}