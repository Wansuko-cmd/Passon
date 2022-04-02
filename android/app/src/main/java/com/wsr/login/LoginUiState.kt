package com.wsr.login

import com.wsr.UserUseCaseModel
import com.wsr.utils.State

data class UserLoginUiState(
    val id: String,
    val displayName: String,
    val databasePath: String,
    val isSelected: Boolean,
) {
    fun copyWithIsSelected(isSelected: Boolean) = this.copy(isSelected = isSelected)
}

fun List<UserLoginUiState>.getSelected() = this.first { it.isSelected }

fun UserUseCaseModel.toLoginUiState() =
    UserLoginUiState(
        id = this.id,
        displayName = displayName,
        databasePath = databasePath,
        isSelected = false,
    )

data class ErrorLoginUiState(val message: String)

data class LoginUiState(
    val users: State<List<UserLoginUiState>, ErrorLoginUiState> = State.Loading
) {
    fun copyWithUsers(users: State<List<UserLoginUiState>, ErrorLoginUiState>) =
        this.copy(users = users)
}
