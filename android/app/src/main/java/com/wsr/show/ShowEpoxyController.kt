package com.wsr.show

import com.airbnb.epoxy.TypedEpoxyController
import com.wsr.showPasswordRow

class ShowEpoxyController() :
    TypedEpoxyController<List<PasswordShowUiState>>() {

    override fun buildModels(list: List<PasswordShowUiState>) {
        list.forEach { password ->
            showPasswordRow {
                id(password.id)
                name(password.name)
                password(if(password.showPassword) password.password else "非表示")
                showPassword(password.showPassword)
                onClickShowPassword { _, _, _, _ -> }
            }
        }
    }
}