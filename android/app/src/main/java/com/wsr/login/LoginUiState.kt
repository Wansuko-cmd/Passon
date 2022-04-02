package com.wsr.login

import com.wsr.utils.State

data class UserLoginUiState(
    val id: String,
    val displayName: String,
    val databasePath: String,
    val isSelected: Boolean,
) {
    fun copyWithIsSelected(isSelected: Boolean) = this.copy(isSelected = isSelected)
}

data class ErrorLoginUiState(val message: String)

data class LoginUiState(
    val users: State<List<UserLoginUiState>, ErrorLoginUiState>
)
