package com.wsr.index

import android.content.res.Resources
import com.airbnb.epoxy.TypedEpoxyController
import com.wsr.R
import com.wsr.indexPasswordGroupRow
import com.wsr.messageRow

class IndexEpoxyController(
    private val onClick: (String) -> Unit,
    private val resources: Resources,
) : TypedEpoxyController<List<PasswordGroupIndexUiState>>() {

    override fun buildModels(list: List<PasswordGroupIndexUiState>) {
        if (list.isEmpty()) {
            messageRow {
                id(MESSAGE_ID)
                message(this@IndexEpoxyController.resources.getString(R.string.index_no_password_group_message))
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

    companion object {
        const val MESSAGE_ID = "index_message_row_id"
    }
}
