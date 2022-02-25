package com.wsr.edit

import com.wsr.password.PasswordUseCaseModel
import com.wsr.passwordgroup.PasswordGroupUseCaseModel
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

data class PasswordGroupEditUiState(
    val id: String,
    val title: String,
    val remark: String,
) {
    fun replaceTitle(title: String) = this.copy(title = title)
    fun replaceRemark(remark: String) = this.copy(remark = remark)

    companion object {
        fun PasswordGroupUseCaseModel.toEditUiState() = PasswordGroupEditUiState(id, title, remark)
    }
}


data class EditContentsUiState(
    val passwordGroup: State<PasswordGroupEditUiState, ErrorEditUiState> = State.Loading,
    val passwords: State<List<PasswordEditUiState>, ErrorEditUiState> = State.Loading,
) {
    fun replacePasswordGroup(passwordGroup: State<PasswordGroupEditUiState, ErrorEditUiState>) =
        this.copy(passwordGroup = passwordGroup)

    fun replacePasswords(passwords: State<List<PasswordEditUiState>, ErrorEditUiState>) =
        this.copy(passwords = passwords)
}


data class ErrorEditUiState(val message: String)


data class EditUiState(
    var titleState: State<String, ErrorEditUiState> = State.Loading,
    var contents: EditContentsUiState = EditContentsUiState(),
)
