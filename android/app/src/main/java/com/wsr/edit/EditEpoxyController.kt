package com.wsr.edit

import com.wsr.editAddPasswordButton
import com.wsr.editPasswordRow
import com.wsr.editRemarkRow
import com.wsr.editTitleRow
import com.wsr.layout.AfterTextChanged
import com.wsr.utils.MyTyped2EpoxyController

class EditEpoxyController(
    private val afterTitleChanged: (newTitle: String) -> Unit,
    private val afterRemarkChanged: (newRemark: String) -> Unit,
    private val afterNameChanged: (passwordPairId: String, newName: String) -> Unit,
    private val afterPasswordChanged: (passwordPairId: String, newPassword: String) -> Unit,
    private val onClickAddPasswordButton: () -> Unit,
) : MyTyped2EpoxyController<PasswordGroupEditUiState, List<PasswordPairEditUiState>>() {

    override fun buildModels(
        passwordGroup: PasswordGroupEditUiState,
        passwordPairs: List<PasswordPairEditUiState>,
    ) {

        editTitleRow {
            id(passwordGroup.id)
            title(passwordGroup.title)
            afterTitleChanged(
                AfterTextChanged(this@EditEpoxyController.afterTitleChanged)
            )
        }

        passwordPairs.forEach { passwordPair ->
            editPasswordRow {
                id(passwordPair.id)
                name(passwordPair.name)
                password(passwordPair.password)
                afterNameChanged(
                    AfterTextChanged(
                        curry(this@EditEpoxyController.afterNameChanged)(passwordPair.id)
                    )
                )
                afterPasswordChanged(
                    AfterTextChanged(
                        curry(this@EditEpoxyController.afterPasswordChanged)(passwordPair.id)
                    )
                )
            }
        }

        editAddPasswordButton {
            id(ADD_PASSWORD_BUTTON_ID)
            onClickAddPasswordButton { _, _, _, _ -> this@EditEpoxyController.onClickAddPasswordButton() }
        }

        editRemarkRow {
            id(passwordGroup.id)
            remark(passwordGroup.remark)
            afterRemarkChanged(
                AfterTextChanged(this@EditEpoxyController.afterRemarkChanged)
            )
        }
    }

    companion object {
        const val ADD_PASSWORD_BUTTON_ID = "add_password_button_id"
    }
}

private fun <T, U, V> curry(f: (T, U) -> V): (T) -> (U) -> V = { t -> { u -> f(t, u) } }
