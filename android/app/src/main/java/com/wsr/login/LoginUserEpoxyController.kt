package com.wsr.login

import android.view.View
import com.airbnb.epoxy.TypedEpoxyController
import com.wsr.loginUserRow

class LoginUserEpoxyController(private val onSelected: (userId: String) -> Unit) : TypedEpoxyController<List<UserLoginUiState>>() {
    override fun buildModels(users: List<UserLoginUiState>) {
        users.forEach { user ->
            loginUserRow {
                id(user.id)
                displayName(user.displayName)
                databasePath(user.databasePath)
                isSelected(user.isSelected)
                onSelected(View.OnClickListener { this@LoginUserEpoxyController.onSelected(user.id) })
            }
        }
    }
}
