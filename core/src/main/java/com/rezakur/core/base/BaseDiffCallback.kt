package com.rezakur.core.base

import androidx.recyclerview.widget.DiffUtil

class BaseDiffCallback<T>(
    private val oldList: List<T>,
    private val newList: List<T>,
    private val areItemsSame: (T, T) -> Boolean,
    private val areContentsSame: (T, T) -> Boolean
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return areItemsSame(oldList[oldItemPosition], newList[newItemPosition])
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return areContentsSame(oldList[oldItemPosition], newList[newItemPosition])
    }
}