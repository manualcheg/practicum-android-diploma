package ru.practicum.android.diploma.search.domain.useCase

class GetFilteringOptionsUseCaseImpl:GetFilteringOptionsUseCase {
    override fun execute(): HashMap<String, String> {
        val options = HashMap<String, String>()
//        options["industry"] = "49" //моковые данные (отрасль: Ритуальные услуги)
        options["area"] = "40"  //мок данные (ареа: Казахстан)
        return options
    }
}