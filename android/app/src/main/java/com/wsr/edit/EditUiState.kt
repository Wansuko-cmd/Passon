package com.wsr.edit

import com.wsr.password.PasswordUseCaseModel
import com.wsr.state.State

data class PasswordEditUiState(
    val id: String,
    val name: String,
    val password: String,
)

fun PasswordUseCaseModel.toEditUiModel() =
    PasswordEditUiState(id, name, password)

data class ErrorEditUiState(val message: String)

data class EditUiState(
    val titleState: State<String, ErrorEditUiState> = State.Loading,
    val passwordsState: State<List<PasswordEditUiState>, ErrorEditUiState> = State.Loading
)