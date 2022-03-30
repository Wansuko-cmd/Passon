package com.wsr.show

import com.wsr.showPasswordRow
import com.wsr.showRemarkRow
import com.wsr.showTitleRow
import com.wsr.utils.MyTyped2EpoxyController

class ShowEpoxyController(
    private val onClickShowPassword: (passwordItemId: String) -> Unit,
    private val onClickPasswordCopy: (PasswordItemShowUiState) -> Unit,
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
        if (passwordItems.isNotEmpty()) {
            passwordItems.forEach { passwordItem ->
                showPasswordRow {
                    id(passwordItem.id)
                    name(passwordItem.name)
                    password(passwordItem.password)
                    showPassword(passwordItem.showPassword)
                    onClickShowPassword { _, _, _, _ ->
                        this@ShowEpoxyController.onClickShowPassword(passwordItem.id)
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
    }
}
