package ru.practicum.android.diploma.common.util.recycleView

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
import ru.practicum.android.diploma.filter.ui.CountryViewHolder
import ru.practicum.android.diploma.filter.ui.RegionIndustryViewHolder
import ru.practicum.android.diploma.filter.ui.model.CountryUi
import ru.practicum.android.diploma.filter.ui.model.RegionIndustryUi
import ru.practicum.android.diploma.vacancy.ui.ContactsPhoneViewHolder

class RVAdapter(
    private var clickListener: ((ItemUiBase)) -> Unit = {}
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items = listOf<ItemUiBase>()
        set(newList) {
            val diffResult = DiffUtil.calculateDiff(
                DiffCallback(field, newList)
            )
            field = newList
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

            COUNTRY_VIEWTYPE -> CountryViewHolder(
                ItemCountryAndRegionBinding.inflate(layoutInflater, parent, false), clickListener
            )

            REGION_INDUSTRY_VIEWTYPE -> RegionIndustryViewHolder(
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
            is CountryUi -> (holder as CountryViewHolder).bind(item)
            is RegionIndustryUi -> (holder as RegionIndustryViewHolder).bind(item)
        }

    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is VacancyUi -> VACANCY_VIEWTYPE
            is PhoneUi -> CONTACTS_PHONE_VIEWTYPE
            is CountryUi -> COUNTRY_VIEWTYPE
            is RegionIndustryUi -> REGION_INDUSTRY_VIEWTYPE
            else -> {
                throw IllegalAccessException("Illegal type: ${items[position]}")
            }
        }
    }

    private companion object {
        const val VACANCY_VIEWTYPE = 1
        const val CONTACTS_PHONE_VIEWTYPE = 2
        const val COUNTRY_VIEWTYPE = 3
        const val REGION_INDUSTRY_VIEWTYPE = 4
    }
}