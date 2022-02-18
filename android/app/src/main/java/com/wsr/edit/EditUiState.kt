package com.wsr.edit

import com.wsr.password.PasswordUseCaseModel
import com.wsr.state.State

data class PasswordEditUiState(
    val id: String,
    val name: String,
    val password: String,
)

fun PasswordUseCaseModel.toEditUiState() =
    PasswordEditUiState(id, name, password)

fun PasswordEditUiState.toUseCaseModel(passwordGroupId: String) =
    PasswordUseCaseModel(id, passwordGroupId, name, password)

data class ErrorEditUiState(val message: String)

data class EditContentsUiState(
    val title: State<String, ErrorEditUiState> = State.Loading,
    val passwords: State<List<PasswordEditUiState>, ErrorEditUiState> = State.Loading,
)

data class EditUiState(
    val titleState: State<String, ErrorEditUiState> = State.Loading,
    val contents: EditContentsUiState = EditContentsUiState(),
)
