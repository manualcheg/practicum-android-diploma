package ru.practicum.android.diploma.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.common.domain.model.vacancy_models.Vacancy

/*
* Использование VacanciesAdapter
*
* Во фрагменте создаем
* private val vacancyAdapter = VacanciesAdapter{
*   clickOnVacancy(it)
* }
*
* Создаем функцию которая производит действие по клику
* private fun clickOnVacancy(vacancy: Vacancy) {
*   // тут что-то делаем, напрмер открываем вакансию
* }
*
* Присваеваем RV
* binding.searchScreenRecyclerView.adapter = vacancyAdapter
*
* В адаптер загружаем ваксии так
* vacancyAdapter.vacancies = [новый список вакансий]
* Дифутил сработает автоматически и перестроит список
*
* Пока всё в одном файле, позже можно разбить на отдельные Adapter, ViewHolder, DiffCallback
* */

class VacanciesAdapter(
    private val clickListener: VacancyClickListener
) : RecyclerView.Adapter<VacancyViewHolder>() {

    var vacancies = listOf<Vacancy>()
        set(newList) {
            val diffResult = DiffUtil.calculateDiff(
                VacancyDiffCallback(field, newList)
            )
            field = newList
            diffResult.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacancyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_vacancy, parent, false)
        return VacancyViewHolder(view)
    }

    override fun getItemCount(): Int = vacancies.size

    override fun onBindViewHolder(holder: VacancyViewHolder, position: Int) {
        holder.bind(vacancies[position])
        holder.itemView.setOnClickListener {
            clickListener.onTrackClick(vacancies[holder.adapterPosition])
        }
    }

    fun interface VacancyClickListener {
        fun onTrackClick(vacancy: Vacancy)
    }
}

class VacancyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val vacancyLogo: ImageView = itemView.findViewById(R.id.vacancyLayoutLogoImageView)
    private val vacancyHeader: TextView = itemView.findViewById(R.id.vacancyHeaderTextView)
    private val vacancyDescription: TextView =
        itemView.findViewById(R.id.vacancyDescriptionTextView)
    private val vacancySalary: TextView = itemView.findViewById(R.id.vacancySalaryTextView)

    fun bind(vacancy: Vacancy) {

        // название вакансии
        vacancyHeader.text = vacancy.name

        // если указан работодатель
        if (vacancy.employer != null) {

            val employer = vacancy.employer

            // название работодателя
            vacancyDescription.text = employer.name

            if (employer.logoUrls != null) {
                // если у работодателя указаны лого
                Glide
                    .with(itemView)
                    .load(employer.logoUrls.logo90)
                    .placeholder(R.drawable.ic_placeholder)
                    .centerCrop()
                    .transform(
                        RoundedCorners(
                            itemView.resources.getDimensionPixelSize(
                                R.dimen.corner_radius
                            )
                        )
                    )
                    .into(vacancyLogo)
            } else {
                // если лого не указаны, то ставим стандартный плейсхолдер
                vacancyLogo.setImageResource(R.drawable.ic_placeholder)
            }

        } else {
            // если работодательнее указан, то обнуляем название и ставим стандартный плейсхолдер
            vacancyDescription.text = ""
            vacancyLogo.setImageResource(R.drawable.ic_placeholder)
        }

        vacancy.salary?.let {
            // У класса Vacancy надо сделать метод возвращающий ЗП или надпись 'ЗП не указана'
            // типа такого
            // vacancySalary.text = vacancy.getSalary()
        }
    }
}

class VacancyDiffCallback(
    private val oldList: List<Vacancy>,
    private val newList: List<Vacancy>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val old = oldList[oldItemPosition]
        val new = newList[newItemPosition]
        return old == new
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val old = oldList[oldItemPosition]
        val new = newList[newItemPosition]
        return old.id == new.id
    }
}