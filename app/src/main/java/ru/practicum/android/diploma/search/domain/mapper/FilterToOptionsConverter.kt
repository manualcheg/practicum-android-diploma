package ru.practicum.android.diploma.search.domain.mapper

import ru.practicum.android.diploma.common.domain.model.filter_models.Filter
import ru.practicum.android.diploma.common.util.constants.FilterConst.AREA
import ru.practicum.android.diploma.common.util.constants.FilterConst.INDUSTRY
import ru.practicum.android.diploma.common.util.constants.FilterConst.ONLY_WITH_SALARY
import ru.practicum.android.diploma.common.util.constants.FilterConst.SALARY

class FilterToOptionsConverter {
    fun map(filter: Filter?): HashMap<String, String> {
        val hashMap: HashMap<String, String> = hashMapOf()

        if (filter != null) {
            if (filter.area != null) {
                hashMap[AREA] = filter.area.id
            } else if (filter.country != null) {
                hashMap[AREA] = filter.country.id
            }

            if (filter.industry != null) {
                hashMap[INDUSTRY] = filter.industry.id
            }

            if (filter.salary != null) {
                hashMap[SALARY] = filter.salary.toString()
            }

            if (filter.onlyWithSalary) {
                hashMap[ONLY_WITH_SALARY] = true.toString()
            }
        }

        return hashMap
    }
}