package com.wsr.show

import com.wsr.messageRow
import com.wsr.showPasswordRow
import com.wsr.showRemarkRow
import com.wsr.utils.MyTyped2EpoxyController

class ShowEpoxyController(
    private val onClickShowPassword: (PasswordShowUiState) -> Unit,
    private val onClickPasswordCopy: (PasswordShowUiState) -> Unit,
    private val noPasswordMessage: String,
) :
    MyTyped2EpoxyController<PasswordGroupShowUiState, List<PasswordShowUiState>>() {

    override fun buildModels(
        passwordGroup: PasswordGroupShowUiState,
        list: List<PasswordShowUiState>
    ) {
        if (list.isEmpty()) {
            messageRow {
                id(MESSAGE_ID)
                message(this@ShowEpoxyController.noPasswordMessage)
            }
        } else {
            list.forEach { password ->
                showPasswordRow {
                    id(password.id)
                    name(password.name)
                    password(password.password)
                    showPassword(password.showPassword)
                    onClickShowPassword { _, _, _, _ ->
                        this@ShowEpoxyController.onClickShowPassword(password)
                    }
                    onClickPasswordCopy { _, _, _, _ ->
                        this@ShowEpoxyController.onClickPasswordCopy(password)
                    }
                }
            }

            showRemarkRow {
                id(passwordGroup.id)
                remark(passwordGroup.remark)
            }
        }
    }

    companion object {
        const val MESSAGE_ID = "show_message_row_id"
    }
}