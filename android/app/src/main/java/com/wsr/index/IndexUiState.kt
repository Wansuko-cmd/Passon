package com.wsr.index

import com.wsr.PasswordGroupUseCaseModel
import com.wsr.utils.State

data class PasswordGroupIndexUiState(
    val id: String,
    val title: String,
)

data class ErrorIndexUiState(val message: String)

fun PasswordGroupUseCaseModel.toIndexUiState() =
    PasswordGroupIndexUiState(id = this.id, title = this.title)

data class IndexUiState(
    val passwordGroupsState: State<List<PasswordGroupIndexUiState>, ErrorIndexUiState> = State.Loading,
)
