package com.wsr.edit

import com.wsr.password.PasswordUseCaseModel
import com.wsr.state.State

data class PasswordEditUiState(
    val id: String,
    val name: String,
    val password: String,
) {
    fun replaceName(name: String) = this.copy(name = name)
    fun replacePassword(password: String) = this.copy(password = password)

    companion object {
        fun PasswordUseCaseModel.toEditUiState() = PasswordEditUiState(id, name, password)

        fun PasswordEditUiState.toUseCaseModel(passwordGroupId: String) =
            PasswordUseCaseModel(id, passwordGroupId, name, password)
    }
}


data class EditContentsUiState(
    val title: State<String, ErrorEditUiState> = State.Loading,
    val passwords: State<List<PasswordEditUiState>, ErrorEditUiState> = State.Loading,
) {
    fun replaceTitle(title: State<String, ErrorEditUiState>) = this.copy(title = title)
    fun replacePasswords(passwords: State<List<PasswordEditUiState>, ErrorEditUiState>) =
        this.copy(passwords = passwords)
}


data class ErrorEditUiState(val message: String)


data class EditUiState(
    val titleState: State<String, ErrorEditUiState> = State.Loading,
    val contents: EditContentsUiState = EditContentsUiState(),
)