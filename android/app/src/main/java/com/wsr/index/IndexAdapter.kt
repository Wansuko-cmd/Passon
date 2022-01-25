package com.wsr.index

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.wsr.databinding.FragmentIndexItemRowBinding

class IndexAdapter : ListAdapter<PasswordGroupIndexUIState, IndexViewHolder>(diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndexViewHolder {
        val view = FragmentIndexItemRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return IndexViewHolder(view)
    }

    override fun onBindViewHolder(holder: IndexViewHolder, position: Int) {
        holder.bindPasswordGroup(getItem(position))
    }
}


private val diffCallback = object : DiffUtil.ItemCallback<PasswordGroupIndexUIState>() {

    override fun areItemsTheSame(
        oldItem: PasswordGroupIndexUIState,
        newItem: PasswordGroupIndexUIState,
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: PasswordGroupIndexUIState,
        newItem: PasswordGroupIndexUIState,
    ): Boolean = oldItem == newItem
}