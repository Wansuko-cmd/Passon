package com.wsr.index

import com.airbnb.epoxy.TypedEpoxyController
import com.wsr.indexPasswordGroupRow
import com.wsr.messageRow
import java.util.*

class IndexEpoxyController(
    private val onClick: (String) -> Unit,
    private val noPasswordGroupMessage: String,
) :
    TypedEpoxyController<List<PasswordGroupIndexUiState>>() {

    override fun buildModels(list: List<PasswordGroupIndexUiState>) {
        if (list.isNotEmpty()) {
            list.forEach { passwordGroup ->
                indexPasswordGroupRow {
                    id(passwordGroup.id)
                    title(passwordGroup.title)
                    onClick { _, _, _, _ ->
                        this@IndexEpoxyController.onClick(passwordGroup.id)
                    }
                }
            }
        } else {
            messageRow {
                id(UUID.randomUUID().toString())
                message(this@IndexEpoxyController.noPasswordGroupMessage)
            }
        }
    }
}