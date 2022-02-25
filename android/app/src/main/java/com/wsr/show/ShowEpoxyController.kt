package com.wsr.show

import com.airbnb.epoxy.TypedEpoxyController
import com.wsr.messageRow
import com.wsr.showPasswordRow
import java.util.*

class ShowEpoxyController(
    private val onClickShowPassword: (PasswordShowUiState) -> Unit,
    private val onClickPasswordCopy: (PasswordShowUiState) -> Unit,
    private val noPasswordMessage: String,
) :
    TypedEpoxyController<List<PasswordShowUiState>>() {

    override fun buildModels(list: List<PasswordShowUiState>) {
        if (list.isNotEmpty()) {
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
        } else {
            val noPasswordMessage = noPasswordMessage
            messageRow {
                id(UUID.randomUUID().toString())
                message(noPasswordMessage)
            }
        }
    }
}