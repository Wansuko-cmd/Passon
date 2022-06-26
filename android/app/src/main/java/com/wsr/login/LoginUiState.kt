package com.wsr.login

import com.wsr.UserUseCaseModel
import com.wsr.utils.State

data class UserLoginUiState(
    val id: String,
    val displayName: String,
    val databasePath: String,
    val isSelected: Boolean,
) {
    companion object {

        fun List<UserLoginUiState>.getSelectedUser(): UserLoginUiState? = this.firstOrNull { it.isSelected }

        /**
         * 指定されたUserを選択状態にする
         */
        fun List<UserLoginUiState>.checkSelectedUser(userId: String) =
            if (this.map { it.id }.contains(userId)) this.map { it.copy(isSelected = it.id == userId) }
            else this.map { it.copy(isSelected = it.id == (this.firstOrNull()?.id ?: false)) }
    }
}

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
)
