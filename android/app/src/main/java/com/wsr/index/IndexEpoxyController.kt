package com.wsr.index

import com.airbnb.epoxy.TypedEpoxyController
import com.wsr.fragmentIndexItemRow

class IndexEpoxyController :
    TypedEpoxyController<List<PasswordGroupIndexUiState>>() {

    override fun buildModels(list: List<PasswordGroupIndexUiState>) {
        list.forEach { passwordGroup ->
            fragmentIndexItemRow {
                id(passwordGroup.id)
                title(passwordGroup.title)
            }
        }
    }
}