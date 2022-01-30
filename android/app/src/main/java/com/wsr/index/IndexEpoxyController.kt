package com.wsr.index

import com.airbnb.epoxy.TypedEpoxyController
import com.wsr.indexPasswordGroupRow

class IndexEpoxyController :
    TypedEpoxyController<List<PasswordGroupIndexUiState>>() {

    override fun buildModels(list: List<PasswordGroupIndexUiState>) {
        list.forEach { passwordGroup ->
            indexPasswordGroupRow {
                id(passwordGroup.id)
                title(passwordGroup.title)
            }
        }
    }
}