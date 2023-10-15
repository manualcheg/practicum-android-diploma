package ru.practicum.android.diploma.filter.data.mapper

import ru.practicum.android.diploma.common.domain.model.filter_models.Country
import ru.practicum.android.diploma.common.domain.model.filter_models.Industry
import ru.practicum.android.diploma.common.domain.model.vacancy_models.Area
import ru.practicum.android.diploma.filter.data.model.AreasResponse
import ru.practicum.android.diploma.filter.data.model.CountriesResponse
import ru.practicum.android.diploma.filter.data.model.IndustriesResponse

class FiltersDtoToDomainConverter {

    fun map(areasResponse: AreasResponse): List<Area> {
        val listOfAreasDto = areasResponse.items
        val setOfArea: MutableSet<Area> = mutableSetOf()
        listOfAreasDto.forEach { areasDto ->
            setOfArea.add(
                Area(
                    name = areasDto.name,
                    id = areasDto.id,
                    parentId = areasDto.parentId
                )
            )
            areasDto.areas.forEach { }
        }



        return emptyList()
    }

    fun map(countriesResponse: CountriesResponse): List<Country> {
        val listOfCountriesDto = countriesResponse.items
        val result = listOfCountriesDto.map {
            Country(
                it.id, it.name
            )
        }
        return result
    }

    fun map(industriesResponse: IndustriesResponse): List<Industry> {
        return emptyList()
    }
}