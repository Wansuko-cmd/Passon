package com.wsr.show

import com.wsr.passwordgroup.PasswordGroupUseCaseModel
import com.wsr.passwordpair.PasswordPairUseCaseModel
import com.wsr.state.State

data class PasswordPairShowUiState(
    val id: String,
    val name: String,
    val password: String,
    val showPassword: Boolean,
) {
    fun copyWithShowPassword(showPassword: Boolean) = this.copy(showPassword = showPassword)

    companion object {
        fun PasswordPairUseCaseModel.toShowUiModel() =
            PasswordPairShowUiState(id = id, name = name, password = password, showPassword = false)
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
    val passwordPairs: State<List<PasswordPairShowUiState>, ErrorShowUiState> = State.Loading,
) {
    fun copyWithPasswordGroup(passwordGroup: State<PasswordGroupShowUiState, ErrorShowUiState>) =
        this.copy(passwordGroup = passwordGroup)

    fun copyWithPasswordPairs(passwords: State<List<PasswordPairShowUiState>, ErrorShowUiState>) =
        this.copy(passwordPairs = passwords)
}

data class ShowUiState(
    val titleState: State<String, ErrorShowUiState> = State.Loading,
    val contents: ShowContentsUiState = ShowContentsUiState(),
)
