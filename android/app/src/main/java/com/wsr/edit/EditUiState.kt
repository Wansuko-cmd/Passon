package com.wsr.edit

import com.wsr.PasswordGroupUseCaseModel
import com.wsr.PasswordItemUseCaseModel
import com.wsr.utils.State

data class PasswordItemEditUiState(
    val id: String,
    val name: String,
    val password: String,
    val shouldShowPassword: Boolean,
) {

    companion object {
        fun PasswordItemUseCaseModel.toEditUiState() =
            PasswordItemEditUiState(id = id, name = name, password = password, shouldShowPassword = false)
        fun PasswordItemEditUiState.toUseCaseModel(passwordGroupId: String) =
            PasswordItemUseCaseModel(id = id, passwordGroupId = passwordGroupId, name = name, password = password)
    }
}

data class PasswordGroupEditUiState(
    val id: String,
    val title: String,
    val remark: String,
) {
    companion object {
        fun PasswordGroupUseCaseModel.toEditUiState() =
            PasswordGroupEditUiState(id = id, title = title, remark = remark)
    }
}

data class EditUiState(
    val passwordGroup: State<PasswordGroupEditUiState, ErrorEditUiState> = State.Loading,
    val passwordItems: State<List<PasswordItemEditUiState>, ErrorEditUiState> = State.Loading,
    val edited: Boolean = false,
) {
    fun mapPasswordGroup(
        passwordGroup: (State<PasswordGroupEditUiState, ErrorEditUiState>) -> State<PasswordGroupEditUiState, ErrorEditUiState>
    ) =
        this.copy(passwordGroup = passwordGroup(this.passwordGroup), edited = true)

    fun mapPasswordItems(
        passwordItems: (State<List<PasswordItemEditUiState>, ErrorEditUiState>) -> State<List<PasswordItemEditUiState>, ErrorEditUiState>
    ) =
        this.copy(passwordItems = passwordItems(this.passwordItems), edited = true)

    fun resetEdited() = this.copy(edited = false)
}

data class ErrorEditUiState(val message: String)
