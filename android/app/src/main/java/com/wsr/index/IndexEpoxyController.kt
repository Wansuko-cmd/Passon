package com.wsr.index

import com.airbnb.epoxy.TypedEpoxyController
import com.wsr.fragmentIndexPasswordGroupRow

class IndexEpoxyController :
    TypedEpoxyController<List<PasswordGroupIndexUiState>>() {

    override fun buildModels(list: List<PasswordGroupIndexUiState>) {
        list.forEach { passwordGroup ->
            fragmentIndexPasswordGroupRow {
                id(passwordGroup.id)
                title(passwordGroup.title)
            }
        }
    }
}