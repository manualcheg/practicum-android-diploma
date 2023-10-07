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
import ru.practicum.android.diploma.common.ui.model.VacancyUi

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

    var vacancies = listOf<VacancyUi>()
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
        fun onTrackClick(vacancy: VacancyUi)
    }
}

class VacancyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val vacancyLogo: ImageView = itemView.findViewById(R.id.vacancyLayoutLogoImageView)
    private val vacancyHeader: TextView = itemView.findViewById(R.id.vacancyHeaderTextView)
    private val vacancyDescription: TextView =
        itemView.findViewById(R.id.vacancyDescriptionTextView)
    private val vacancySalary: TextView = itemView.findViewById(R.id.vacancySalaryTextView)

    fun bind(vacancy: VacancyUi) {
        if (vacancy.areaName.isNotBlank()){
            val header = buildString {
                append(vacancy.name)
                append(", ")
                append(vacancy.areaName)
            }
            vacancyHeader.text = header
        } else vacancyHeader.text = vacancy.name

        vacancyDescription.text = vacancy.employerName
        Glide.with(itemView).load(vacancy.employerLogoUrl90).placeholder(R.drawable.ic_placeholder)
            .centerCrop().transform(
                RoundedCorners(
                    itemView.resources.getDimensionPixelSize(
                        R.dimen.corner_radius
                    )
                )
            ).into(vacancyLogo)
        vacancySalary.text = vacancy.salaryAmount
    }
}

class VacancyDiffCallback(
    private val oldList: List<VacancyUi>, private val newList: List<VacancyUi>
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