package com.wsr.show

import com.airbnb.epoxy.TypedEpoxyController
import com.wsr.layout.InputType
import com.wsr.showPasswordRow

class ShowEpoxyController(
    private val onClickShowPassword: (PasswordShowUiState) -> Unit,
    private val onClickPasswordCopy: (PasswordShowUiState) -> Unit,
) :
    TypedEpoxyController<List<PasswordShowUiState>>() {

    override fun buildModels(list: List<PasswordShowUiState>) {
        list.forEach { password ->
            showPasswordRow {
                id(password.id)
                name(password.name)
                password(password.password)
                showPassword(password.showPassword)
                passwordStatus(if(password.showPassword) InputType.Text else InputType.TextPassword)
                onClickShowPassword { _, _, _, _ ->
                    this@ShowEpoxyController.onClickShowPassword(password)
                }
                onClickPasswordCopy { _, _, _, _ ->
                    this@ShowEpoxyController.onClickPasswordCopy(password)
                }
            }
        }
    }
}