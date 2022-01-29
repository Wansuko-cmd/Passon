package com.wsr.index

import com.airbnb.epoxy.TypedEpoxyController
import com.wsr.fragmentIndexModelPasswordGroup

class IndexEpoxyController :
    TypedEpoxyController<List<PasswordGroupIndexUiState>>() {

    override fun buildModels(list: List<PasswordGroupIndexUiState>) {
        list.forEach { passwordGroup ->
            fragmentIndexModelPasswordGroup {
                id(passwordGroup.id)
                title(passwordGroup.title)
            }
        }
    }
}