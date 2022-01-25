package com.wsr.index

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.wsr.databinding.FragmentIndexItemRowBinding

class IndexAdapter : ListAdapter<PasswordGroupIndexUiState, IndexViewHolder>(diffCallback) {
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


private val diffCallback = object : DiffUtil.ItemCallback<PasswordGroupIndexUiState>() {

    override fun areItemsTheSame(
        oldItem: PasswordGroupIndexUiState,
        newItem: PasswordGroupIndexUiState,
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: PasswordGroupIndexUiState,
        newItem: PasswordGroupIndexUiState,
    ): Boolean = oldItem == newItem
}