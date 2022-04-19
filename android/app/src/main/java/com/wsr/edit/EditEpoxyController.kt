package com.wsr.edit

import android.view.View
import com.wsr.editAddPasswordButton
import com.wsr.editPasswordRow
import com.wsr.editRemarkRow
import com.wsr.editTitleRow
import com.wsr.AfterTextChanged
import com.wsr.utils.MyTyped2EpoxyController

class EditEpoxyController(
    private val afterTitleChanged: (newTitle: String) -> Unit,
    private val afterRemarkChanged: (newRemark: String) -> Unit,
    private val afterNameChanged: (passwordItemId: String, newName: String) -> Unit,
    private val afterPasswordChanged: (passwordItemId: String, newPassword: String) -> Unit,
    private val onClickDeletePasswordItemButton: (passwordItemId: String) -> Unit,
    private val onClickAddPasswordButton: () -> Unit,
    private val onClickShowPassword: (passwordItemId: String) -> Unit,
) : MyTyped2EpoxyController<PasswordGroupEditUiState, List<PasswordItemEditUiState>>() {

    override fun buildModels(
        passwordGroup: PasswordGroupEditUiState,
        passwordItems: List<PasswordItemEditUiState>,
    ) {

        editTitleRow {
            id(passwordGroup.id)
            title(passwordGroup.title)
            afterTitleChanged(
                com.wsr.AfterTextChanged(this@EditEpoxyController.afterTitleChanged)
            )
        }

        passwordItems.forEach { passwordItem ->
            editPasswordRow {
                id(passwordItem.id)
                name(passwordItem.name)
                password(passwordItem.password)
                showPassword(passwordItem.showPassword)
                onClickShowPassword { _, _, _, _ ->
                    this@EditEpoxyController.onClickShowPassword(passwordItem.id)
                }
                afterNameChanged(
                    com.wsr.AfterTextChanged(
                        curry(this@EditEpoxyController.afterNameChanged)(passwordItem.id)
                    )
                )
                afterPasswordChanged(
                    com.wsr.AfterTextChanged(
                        curry(this@EditEpoxyController.afterPasswordChanged)(passwordItem.id)
                    )
                )
                onClickDeletePasswordItemButton(
                    View.OnClickListener {
                        this@EditEpoxyController.onClickDeletePasswordItemButton(passwordItem.id)
                    }
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
                com.wsr.AfterTextChanged(this@EditEpoxyController.afterRemarkChanged)
            )
        }
    }

    companion object {
        const val ADD_PASSWORD_BUTTON_ID = "add_password_button_id"
    }
}

private fun <T, U, V> curry(f: (T, U) -> V): (T) -> (U) -> V = { t -> { u -> f(t, u) } }
