package com.wsr.show

import com.wsr.password.PasswordUseCaseModel
import com.wsr.passwordgroup.PasswordGroupUseCaseModel
import com.wsr.state.State

data class PasswordShowUiState(
    val id: String,
    val name: String,
    val password: String,
    val showPassword: Boolean,
) {
    fun replaceShowPassword(showPassword: Boolean) = this.copy(showPassword = showPassword)

    companion object {
        fun PasswordUseCaseModel.toShowUiModel() =
            PasswordShowUiState(id = id, name = name, password = password, showPassword = false)
    }
}

data class PasswordGroupShowUiState(
    val id: String,
    val remark: String,
) {
    companion object {
        fun PasswordGroupUseCaseModel.toShowUiModel() =
            PasswordGroupShowUiState(id = id, remark = remark)
    }
}

data class ErrorShowUiState(val message: String)

data class ShowContentsUiState(
    val passwordGroup: State<PasswordGroupShowUiState, ErrorShowUiState> = State.Loading,
    val passwords: State<List<PasswordShowUiState>, ErrorShowUiState> = State.Loading,
) {
    fun replacePasswordGroup(passwordGroup: State<PasswordGroupShowUiState, ErrorShowUiState>) =
        this.copy(passwordGroup = passwordGroup)

    fun replacePasswords(passwords: State<List<PasswordShowUiState>, ErrorShowUiState>) =
        this.copy(passwords = passwords)
}

data class ShowUiState(
    val titleState: State<String, ErrorShowUiState> = State.Loading,
    val contents: ShowContentsUiState = ShowContentsUiState(),
)
