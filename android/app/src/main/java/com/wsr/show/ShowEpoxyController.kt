package com.wsr.show

import com.airbnb.epoxy.TypedEpoxyController
import com.wsr.showPasswordRow

class ShowEpoxyController :
    TypedEpoxyController<List<PasswordShowUiState>>() {

    override fun buildModels(list: List<PasswordShowUiState>) {
        list.forEach { password ->
            showPasswordRow {
                id(password.id)
                name(password.name)
                password(password.password)
            }
        }
    }
}