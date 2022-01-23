package com.wsr.index

import androidx.recyclerview.widget.RecyclerView
import com.wsr.databinding.FragmentIndexItemRowBinding
import com.wsr.passwordgroup.ExternalPasswordGroup

class IndexViewHolder(
    private val binding: FragmentIndexItemRowBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(passwordGroup: ExternalPasswordGroup) {
        binding.title.text = passwordGroup.title
    }
}