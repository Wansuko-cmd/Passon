package com.wsr.edit

import com.airbnb.epoxy.TypedEpoxyController
import com.wsr.editPasswordRow
import com.wsr.editTitleRow
import com.wsr.layout.AfterTextChanged
import com.wsr.state.consume

class EditEpoxyController(
    private val afterTitleChanged: (newTitle: String) -> Unit,
    private val afterNameChanged: (passwordId: String, newName: String) -> Unit,
    private val afterPasswordChanged: (passwordId: String, newPassword: String) -> Unit,
) : TypedEpoxyController<EditContentsUiState>() {

    override fun buildModels(contents: EditContentsUiState) {

        contents.title.consume(
            success = {
                editTitleRow {
                    id("KEY")
                    title(it)
                    afterTitleChanged(
                        AfterTextChanged(this@EditEpoxyController.afterTitleChanged)
                    )
                }
            },
            failure = {},
            loading = {},
        )


        contents.passwords.consume(
            success = { list ->
                list.forEach { password ->
                    editPasswordRow {
                        id(password.id)
                        name(password.name)
                        password(password.password)
                        afterNameChanged(
                            AfterTextChanged(
                                lift(this@EditEpoxyController.afterNameChanged)(password.id)
                            )
                        )
                        afterPasswordChanged(
                            AfterTextChanged(
                                lift(this@EditEpoxyController.afterPasswordChanged)(password.id)
                            )
                        )
                    }
                }
            },
            failure = {},
            loading = {},
        )
    }
}

fun <T, U, V> lift(f: (T, U) -> V): (T) -> (U) -> V = { t -> { u -> f(t, u) } }