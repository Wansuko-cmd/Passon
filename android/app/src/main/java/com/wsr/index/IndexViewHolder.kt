package com.wsr.index

import androidx.recyclerview.widget.RecyclerView
import com.wsr.databinding.FragmentIndexItemRowBinding

class IndexViewHolder(
    private val binding: FragmentIndexItemRowBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bindPasswordGroup(passwordGroup: PasswordGroupIndexUIModel) {
        binding.title.text = passwordGroup.title
    }
}