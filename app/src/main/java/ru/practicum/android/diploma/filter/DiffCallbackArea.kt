package ru.practicum.android.diploma.filter

import androidx.recyclerview.widget.DiffUtil
import ru.practicum.android.diploma.filter.ui.model.AreaCountryUi

class DiffCallbackArea(
    private val oldList: List<AreaCountryUi>, private val newList: List<AreaCountryUi>
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
        return old.id == new.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val old = oldList[oldItemPosition]
        val new = newList[newItemPosition]
        return old == new
    }
}