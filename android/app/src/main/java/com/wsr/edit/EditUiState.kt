package com.wsr.edit

import com.wsr.passwordgroup.PasswordGroupUseCaseModel
import com.wsr.passworditem.PasswordItemUseCaseModel
import com.wsr.state.State

data class PasswordItemEditUiState(
    val id: String,
    val name: String,
    val password: String,
) {
    fun copyWithName(name: String) = this.copy(name = name)
    fun copyWithPassword(password: String) = this.copy(password = password)

    companion object {
        fun PasswordItemUseCaseModel.toEditUiState() =
            PasswordItemEditUiState(id = id, name = name, password = password)
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
        fun PasswordGroupUseCaseModel.toEditUiState() =
            PasswordGroupEditUiState(id = id, title = title, remark = remark)
    }
}

data class EditContentsUiState(
    val passwordGroup: State<PasswordGroupEditUiState, ErrorEditUiState> = State.Loading,
    val passwordItems: State<List<PasswordItemEditUiState>, ErrorEditUiState> = State.Loading,
) {
    fun copyWithPasswordGroup(passwordGroup: State<PasswordGroupEditUiState, ErrorEditUiState>) =
        this.copy(passwordGroup = passwordGroup)

    fun copyWithPasswordItems(passwordItems: State<List<PasswordItemEditUiState>, ErrorEditUiState>) =
        this.copy(passwordItems = passwordItems)
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
