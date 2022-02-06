package com.example.dailystandup.utils

import androidx.recyclerview.widget.RecyclerView

abstract class DynamicAdapter<T, U: RecyclerView.ViewHolder>(
    protected val dataSet: MutableList<T>
): RecyclerView.Adapter<U>() {

    open fun handle(newList: List<T>) {
        val oldList = dataSet.toList()

        if (oldList.size > newList.size) {
            handleRemovals(newList, oldList.size)
        } else if (oldList.size < newList.size) {
            handleInsertions(newList, oldList.size)
        }

        newList.zip(oldList).forEachIndexed { index, pair ->
            if (pair.first != pair.second) {
                notifyItemChanged(index)
            }
        }
    }

    open fun handleInsertions(newList: List<T>, oldSize: Int) {
        notifyItemRangeInserted(oldSize, newList.size)
    }

    open fun handleRemovals(newList: List<T>, oldSize: Int) {
        notifyItemRangeRemoved(newList.size, oldSize)
    }
}