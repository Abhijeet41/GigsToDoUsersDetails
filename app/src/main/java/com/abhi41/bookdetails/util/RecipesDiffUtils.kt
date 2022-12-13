package com.abhi41.bookdetails.util

import androidx.recyclerview.widget.DiffUtil

class UsersDiffUtils<T>(
    //we replace Result model with generic T
    // because we use diffutil in Ingredients Adapter as well with different model class like Extendedgredient
    private val oldUserList: List<T>,
    private val newUserList: List<T>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldUserList.size
    }

    override fun getNewListSize(): Int {
        return newUserList.size
    }

    //=== operator is used to compare the reference of two variable or object
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldUserList[oldItemPosition] === newUserList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldUserList[oldItemPosition] == newUserList[newItemPosition]
    }


}