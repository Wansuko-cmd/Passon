package com.wsr.index

import com.wsr.passwordgroup.PasswordGroupUseCaseModel
import com.wsr.utils.State

data class PasswordGroupIndexUiState(
    val id: String,
    val title: String,
)

data class ErrorIndexUiState(val message: String)

fun PasswordGroupUseCaseModel.toIndexUiState() =
    PasswordGroupIndexUiState(this.id, this.title)

data class IndexUiState(
    val passwordGroupsState: State<List<PasswordGroupIndexUiState>, ErrorIndexUiState> = State.Loading,
)
