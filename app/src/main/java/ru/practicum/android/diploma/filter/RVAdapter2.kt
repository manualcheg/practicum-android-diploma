package ru.practicum.android.diploma.common.util.recycleView

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.common.ui.model.PhoneUi
import ru.practicum.android.diploma.common.ui.model.VacancyUi
import ru.practicum.android.diploma.databinding.ItemCountryAndRegionBinding
import ru.practicum.android.diploma.databinding.ItemPhonesBinding
import ru.practicum.android.diploma.databinding.ItemSectorBinding
import ru.practicum.android.diploma.databinding.ItemVacancyBinding
import ru.practicum.android.diploma.filter.ui.AreaCountryViewHolder
import ru.practicum.android.diploma.filter.ui.IndustryViewHolder
import ru.practicum.android.diploma.filter.ui.model.IndustryUi
import ru.practicum.android.diploma.filter.ui.model.RegionCountryUi
import ru.practicum.android.diploma.vacancy.ui.ContactsPhoneViewHolder

class RVAdapter2(
    private var clickListener: (ItemUiBase) -> Unit = {}
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val items = mutableListOf<ItemUiBase>()
    fun set(newList: List<ItemUiBase>) {
        val diffResult = DiffUtil.calculateDiff(
            DiffCallback(items, newList)
        )
        items.clear()
        items.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            VACANCY_VIEWTYPE -> VacancyViewHolder(
                ItemVacancyBinding.inflate(
                    layoutInflater, parent, false
                )
            )

            CONTACTS_PHONE_VIEWTYPE -> ContactsPhoneViewHolder(
                ItemPhonesBinding.inflate(
                    layoutInflater, parent, false
                ), clickListener
            )

            COUNTRY_AREA_VIEWTYPE -> AreaCountryViewHolder(
                ItemCountryAndRegionBinding.inflate(layoutInflater, parent, false)
            )

            INDUSTRY_VIEWTYPE -> IndustryViewHolder(
                ItemSectorBinding.inflate(layoutInflater, parent, false)
            )

            else -> throw IllegalAccessException("Illegal type: $viewType")
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = items[position]) {
            is VacancyUi -> {
                (holder as VacancyViewHolder).bind(item)
                holder.itemView.setOnClickListener {
                    clickListener(items[holder.adapterPosition])
                }
            }

            is PhoneUi -> (holder as ContactsPhoneViewHolder).bind(item)
            is RegionCountryUi -> {
                (holder as AreaCountryViewHolder).bind(item)
                holder.itemView.setOnClickListener {
                    clickListener(items[holder.adapterPosition])
                }
            }

            is IndustryUi -> {
                (holder as IndustryViewHolder)
                    .bind(item)
                holder.itemView.setOnClickListener {
                    clickListener(items[holder.adapterPosition])
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is VacancyUi -> VACANCY_VIEWTYPE
            is PhoneUi -> CONTACTS_PHONE_VIEWTYPE
            is RegionCountryUi -> COUNTRY_AREA_VIEWTYPE
            is IndustryUi -> INDUSTRY_VIEWTYPE
            else -> {
                throw IllegalAccessException("Illegal type: ${items[position]}")
            }
        }
    }

    fun printItem() {
        items.forEach { item ->
            Log.d("judjin", item.toString())
        }
    }

    private companion object {
        const val VACANCY_VIEWTYPE = 1
        const val CONTACTS_PHONE_VIEWTYPE = 2
        const val COUNTRY_AREA_VIEWTYPE = 3
        const val INDUSTRY_VIEWTYPE = 4
    }
}