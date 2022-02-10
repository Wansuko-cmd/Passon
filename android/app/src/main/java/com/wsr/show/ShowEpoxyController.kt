package com.wsr.show

import com.airbnb.epoxy.TypedEpoxyController
import com.wsr.showPasswordRow
import java.util.*

class ShowEpoxyController(private val onClickShowPassword: (PasswordShowUiState) -> Unit) :
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
            }
        }
    }
}