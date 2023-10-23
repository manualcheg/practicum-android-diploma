package ru.practicum.android.diploma.common.util.recycleView

import androidx.recyclerview.widget.DiffUtil

class DiffCallback(
    private val oldList: List<ItemUiBase>, private val newList: List<ItemUiBase>
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
        return old.isSelected == new.isSelected && old == new
    }
}