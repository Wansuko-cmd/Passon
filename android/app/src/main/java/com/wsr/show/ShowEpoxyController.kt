package com.wsr.show

import android.content.res.Resources
import com.wsr.R
import com.wsr.messageRow
import com.wsr.showPasswordRow
import com.wsr.showRemarkRow
import com.wsr.utils.MyTyped2EpoxyController

class ShowEpoxyController(
    private val onClickShowPassword: (PasswordPairShowUiState) -> Unit,
    private val onClickPasswordCopy: (PasswordPairShowUiState) -> Unit,
    private val resources: Resources,
) :
    MyTyped2EpoxyController<PasswordGroupShowUiState, List<PasswordPairShowUiState>>() {

    override fun buildModels(
        passwordGroup: PasswordGroupShowUiState,
        passwordPairs: List<PasswordPairShowUiState>,
    ) {
        if (passwordPairs.isEmpty()) {
            messageRow {
                id(MESSAGE_ID)
                message(this@ShowEpoxyController.resources.getString(R.string.show_no_password_message))
            }
        } else {
            passwordPairs.forEach { passwordPair ->
                showPasswordRow {
                    id(passwordPair.id)
                    name(passwordPair.name)
                    password(passwordPair.password)
                    showPassword(passwordPair.showPassword)
                    onClickShowPassword { _, _, _, _ ->
                        this@ShowEpoxyController.onClickShowPassword(passwordPair)
                    }
                    onClickPasswordCopy { _, _, _, _ ->
                        this@ShowEpoxyController.onClickPasswordCopy(passwordPair)
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
