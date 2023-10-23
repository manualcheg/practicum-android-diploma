package ru.practicum.android.diploma.filter.data.mapper

import ru.practicum.android.diploma.common.domain.model.filter_models.AreaFilter
import ru.practicum.android.diploma.common.domain.model.filter_models.CountryFilter
import ru.practicum.android.diploma.common.domain.model.filter_models.IndustryFilter
import ru.practicum.android.diploma.filter.data.model.AreasResponse
import ru.practicum.android.diploma.filter.data.model.CountriesResponse
import ru.practicum.android.diploma.filter.data.model.IndustriesResponse
import ru.practicum.android.diploma.filter.data.model.dto.AreasDto
import ru.practicum.android.diploma.filter.data.model.dto.IndustriesDto

class FiltersDtoToDomainConverter {
    fun convertAreaResponseToListOfAreaWithoutCountries(
        areasResponse: AreasResponse, countriesResponse: CountriesResponse
    ): List<AreaFilter> {
        val listOfAreasDto = areasResponse.items
        val areas: MutableList<AreaFilter> = mutableListOf()
        listOfAreasDto.forEach { areasDto ->
            fromTreeToListArea(root = areasDto, areas, areasDto.name)
        }
        val countries = convertCountriesResponseToListOfCountries(countriesResponse)
        return filterCountriesFromAreas(countries, areas)
    }

    fun convertIndustryResponseToListOfIndustries(industriesResponse: IndustriesResponse): List<IndustryFilter> {
        val listOfIndustryDto = industriesResponse.items
        val listOfIndustry = mutableListOf<IndustryFilter>()
        listOfIndustryDto.forEach { industriesDto ->
            fromTreeToListIndustry(industriesDto, listOfIndustry)
        }
        listOfIndustry.forEachIndexed { index, industryFilter ->
            industryFilter.id = index
        }
        return listOfIndustry
    }

    fun convertCountriesResponseToListOfCountries(countriesResponse: CountriesResponse): List<CountryFilter> {
        val listOfCountriesDto = countriesResponse.items
        val result = listOfCountriesDto.map {
            CountryFilter(
                it.id, it.name
            )
        }
        return result
    }

    private fun fromTreeToListArea(
        root: AreasDto, saveList: MutableList<AreaFilter>, countryName: String
    ) {
        saveList.add(
            AreaFilter(
                id = root.id, name = root.name, countryName = countryName
            )
        )
        if (root.areas.isEmpty()) {
            return
        } else for (area in root.areas) {
            fromTreeToListArea(area, saveList, countryName)
        }
    }

    private fun fromTreeToListIndustry(
        root: IndustriesDto, saveList: MutableList<IndustryFilter>
    ) {
        saveList.add(
            IndustryFilter(
                id = 0,
                industryId = root.id,
                name = root.name,
            )
        )
        if (root.industries.isNullOrEmpty()) {
            return
        } else for (industry in root.industries) {
            fromTreeToListIndustry(industry, saveList)
        }
    }

    private fun filterCountriesFromAreas(
        countries: List<CountryFilter>, areas: List<AreaFilter>
    ): List<AreaFilter> {
        return areas.filter { area ->
            countries.none { area.id == it.id }
        }
    }
}