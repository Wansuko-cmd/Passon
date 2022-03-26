package com.wsr.show

import android.content.res.Resources
import com.wsr.R
import com.wsr.messageRow
import com.wsr.showPasswordRow
import com.wsr.showRemarkRow
import com.wsr.showTitleRow
import com.wsr.utils.MyTyped2EpoxyController

class ShowEpoxyController(
    private val onClickShowPassword: (PasswordItemShowUiState) -> Unit,
    private val onClickPasswordCopy: (PasswordItemShowUiState) -> Unit,
    private val resources: Resources,
) :
    MyTyped2EpoxyController<PasswordGroupShowUiState, List<PasswordItemShowUiState>>() {

    override fun buildModels(
        passwordGroup: PasswordGroupShowUiState,
        passwordItems: List<PasswordItemShowUiState>,
    ) {
        showTitleRow {
            id(TITLE_ID)
            title(passwordGroup.title)
        }
        if (passwordItems.isEmpty()) {
            messageRow {
                id(MESSAGE_ID)
                message(this@ShowEpoxyController.resources.getString(R.string.show_no_password_message))
            }
        } else {
            passwordItems.forEach { passwordItem ->
                showPasswordRow {
                    id(passwordItem.id)
                    name(passwordItem.name)
                    password(passwordItem.password)
                    showPassword(passwordItem.showPassword)
                    onClickShowPassword { _, _, _, _ ->
                        this@ShowEpoxyController.onClickShowPassword(passwordItem)
                    }
                    onClickPasswordCopy { _, _, _, _ ->
                        this@ShowEpoxyController.onClickPasswordCopy(passwordItem)
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
        const val TITLE_ID = "show_title_row_id"
        const val MESSAGE_ID = "show_message_row_id"
    }
}
