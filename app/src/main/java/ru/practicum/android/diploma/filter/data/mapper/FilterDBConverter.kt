package ru.practicum.android.diploma.filter.data.mapper

import ru.practicum.android.diploma.filter.data.model.dto.Filter

class FilterDBConverter {
    fun map(filter: Filter): HashMap<String, String> {
        val hashMap: HashMap<String, String> = hashMapOf()
        filter.apply {
            hashMap["${country?.id}"] = country?.name.toString()
            hashMap["${area?.id}"] = area?.name.toString()
            hashMap["${industry?.id}"] = industry?.name.toString()
            if (salary != null) {
                hashMap["salary"] = salary.toString()
            }
            if (onlyWithSalary == true) {
                hashMap["onlyWithSalary"] = true.toString()
            }
        }
        return hashMap
    }
}