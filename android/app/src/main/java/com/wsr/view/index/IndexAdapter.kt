package com.wsr.view.index

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.wsr.databinding.FragmentIndexItemRowBinding
import com.wsr.password_group.ExternalPasswordGroup

class IndexAdapter : ListAdapter<ExternalPasswordGroup, IndexViewHolder>(diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndexViewHolder {
        val view = FragmentIndexItemRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return IndexViewHolder(view)
    }

    override fun onBindViewHolder(holder: IndexViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}



private val diffCallback = object : DiffUtil.ItemCallback<ExternalPasswordGroup>() {

    override fun areItemsTheSame(
        oldItem: ExternalPasswordGroup,
        newItem: ExternalPasswordGroup
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: ExternalPasswordGroup,
        newItem: ExternalPasswordGroup
    ): Boolean = oldItem == newItem
}