package com.wsr.show

import com.wsr.password.PasswordUseCaseModel
import com.wsr.state.State

data class PasswordShowUiState(
    val id: String,
    val name: String,
    val password: String,
    val showPassword: Boolean,
)

data class ErrorShowUiState(val message: String)

fun PasswordUseCaseModel.toShowUiModel() =
    PasswordShowUiState(this.id, this.name, this.password, false)

data class ShowUiState(
    val title: State<String, ErrorShowUiState> = State.Loading,
    val passwordsState: State<List<PasswordShowUiState>, ErrorShowUiState> = State.Loading,
)