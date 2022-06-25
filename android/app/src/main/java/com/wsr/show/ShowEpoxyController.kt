package com.wsr.show

import com.wsr.showPasswordRow
import com.wsr.showRemarkRow
import com.wsr.showTitleRow
import com.wsr.utils.RefreshableTyped2EpoxyController

class ShowEpoxyController(
    private val onClickShouldShowPassword: (passwordItemId: String) -> Unit,
    private val onClickPasswordCopy: (PasswordItemShowUiState) -> Unit,
) :
    RefreshableTyped2EpoxyController<PasswordGroupShowUiState, List<PasswordItemShowUiState>>() {

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
                    shouldShowPassword(passwordItem.shouldShowPassword)
                    onClickShouldShowPassword { _, _, _, _ ->
                        this@ShowEpoxyController.onClickShouldShowPassword(passwordItem.id)
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
