package com.wsr.show

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import com.airbnb.epoxy.TypedEpoxyController
import com.wsr.showPasswordRow

class ShowEpoxyController(
    private val onClickShowPassword: (PasswordShowUiState) -> Unit,
    private val onClickPasswordCopy: (PasswordShowUiState) -> Unit,
) :
    TypedEpoxyController<List<PasswordShowUiState>>() {

    override fun buildModels(list: List<PasswordShowUiState>) {
        list.forEach { password ->
            showPasswordRow {
                id(password.hashCode())
                name(password.name)
                password(if (password.showPassword) password.password else "非表示")
                showPassword(password.showPassword)
                onClickShowPassword { _, _, _, _ ->
                    this@ShowEpoxyController.onClickShowPassword(
                        password.copy(showPassword = !password.showPassword)
                    )
                }
                onClickPasswordCopy { _, _, _, _ ->
                    this@ShowEpoxyController.onClickPasswordCopy(password)
                }
            }
        }
    }
}