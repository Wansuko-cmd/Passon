package com.wsr.index

import com.airbnb.epoxy.TypedEpoxyController
import com.wsr.indexPasswordGroupRow
import com.wsr.messageRow

class IndexEpoxyController(
    private val onClick: (String) -> Unit,
    private val noPasswordGroupMessage: String,
) :
    TypedEpoxyController<List<PasswordGroupIndexUiState>>() {

    override fun buildModels(list: List<PasswordGroupIndexUiState>) {
        if (list.isEmpty()) {
            messageRow {
                id(this@IndexEpoxyController.noPasswordGroupMessage.hashCode())
                message(this@IndexEpoxyController.noPasswordGroupMessage)
            }
        } else {
            list.forEach { passwordGroup ->
                indexPasswordGroupRow {
                    id(passwordGroup.id)
                    title(passwordGroup.title)
                    onClick { _, _, _, _ ->
                        this@IndexEpoxyController.onClick(passwordGroup.id)
                    }
                }
            }
        }
    }
}