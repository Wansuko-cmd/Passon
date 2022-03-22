package com.wsr.show

import com.wsr.passwordgroup.PasswordGroupUseCaseModel
import com.wsr.passworditem.PasswordItemUseCaseModel
import com.wsr.state.State

data class PasswordItemShowUiState(
    val id: String,
    val name: String,
    val password: String,
    val showPassword: Boolean,
) {
    fun copyWithShowPassword(showPassword: Boolean) = this.copy(showPassword = showPassword)

    companion object {
        fun PasswordItemUseCaseModel.toShowUiModel() =
            PasswordItemShowUiState(id = id, name = name, password = password, showPassword = false)
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
    val passwordItems: State<List<PasswordItemShowUiState>, ErrorShowUiState> = State.Loading,
) {
    fun copyWithPasswordGroup(passwordGroup: State<PasswordGroupShowUiState, ErrorShowUiState>) =
        this.copy(passwordGroup = passwordGroup)

    fun copyWithPasswordItems(passwords: State<List<PasswordItemShowUiState>, ErrorShowUiState>) =
        this.copy(passwordItems = passwords)
}

data class ShowUiState(
    val titleState: State<String, ErrorShowUiState> = State.Loading,
    val contents: ShowContentsUiState = ShowContentsUiState(),
)
