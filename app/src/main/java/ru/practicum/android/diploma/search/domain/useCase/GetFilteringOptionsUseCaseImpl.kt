package ru.practicum.android.diploma.search.domain.useCase

class GetFilteringOptionsUseCaseImpl:GetFilteringOptionsUseCase {
    override fun execute(): HashMap<String, String> {
        val options = HashMap<String, String>()
//        options["industry"] = "49" //пока моковые данные (отрасль: Ритуальные услуги, взято из примера документации)
//        options["area"] = "40"  //пока моковые данные (ареа: Казахстан)
        return options
    }
}