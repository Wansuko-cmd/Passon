package com.wsr.edit

import com.airbnb.epoxy.Typed2EpoxyController
import com.airbnb.epoxy.TypedEpoxyController
import com.wsr.editPasswordRow
import com.wsr.editTitleRow
import com.wsr.layout.AfterTextChanged

class EditEpoxyController(
    private val afterNameChanged: (passwordId: String, newName: String) -> Unit,
    private val afterPasswordChanged: (passwordId: String, newPassword: String) -> Unit,
) : TypedEpoxyController<List<PasswordEditUiState>>(){

    override fun buildModels(list: List<PasswordEditUiState>) {

        editTitleRow {
            id("Id")
            title("Title")
            afterTitleChanged(
                AfterTextChanged {  }
            )
        }

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
    }
}

fun <T, U, V> lift(f: (T, U) -> V): (T) -> (U) -> V = { t -> { u -> f(t, u) } }