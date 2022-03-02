package com.wsr.edit

import com.wsr.password.PasswordUseCaseModel
import com.wsr.passwordgroup.PasswordGroupUseCaseModel
import com.wsr.state.State
import java.util.*

data class PasswordEditUiState(
    val id: String,
    val name: String,
    val password: String,
) {
    fun copyWithName(name: String) = this.copy(name = name)
    fun copyWithPassword(password: String) = this.copy(password = password)

    companion object {
        fun PasswordUseCaseModel.toEditUiState() = PasswordEditUiState(id, name, password)
    }
}

data class PasswordGroupEditUiState(
    val id: String,
    val title: String,
    val remark: String,
) {
    fun copyWithTitle(title: String) = this.copy(title = title)
    fun copyWithRemark(remark: String) = this.copy(remark = remark)

    companion object {
        fun PasswordGroupUseCaseModel.toEditUiState() = PasswordGroupEditUiState(id, title, remark)
    }
}


data class EditContentsUiState(
    val passwordGroup: State<PasswordGroupEditUiState, ErrorEditUiState> = State.Loading,
    val passwords: State<List<PasswordEditUiState>, ErrorEditUiState> = State.Loading,
) {
    fun copyWithPasswordGroup(passwordGroup: State<PasswordGroupEditUiState, ErrorEditUiState>) =
        this.copy(passwordGroup = passwordGroup)

    fun copyWithPasswords(passwords: State<List<PasswordEditUiState>, ErrorEditUiState>) =
        this.copy(passwords = passwords)
}


data class ErrorEditUiState(val message: String)


data class EditUiState(
    val titleState: State<String, ErrorEditUiState> = State.Loading,
    val contents: EditContentsUiState = EditContentsUiState(),
) {
    fun copyWithTitle(titleState: State<String, ErrorEditUiState>) =
        this.copy(titleState = titleState)

    fun copyWithContents(contents: EditContentsUiState) = this.copy(contents = contents)
}
