package com.wsr.index

import com.wsr.passwordgroup.PasswordGroupUseCaseModel

data class PasswordGroupIndexUiState(
    val id: String,
    val title: String,
)

fun PasswordGroupUseCaseModel.toIndexUiState() =
    PasswordGroupIndexUiState(this.id, this.title)

data class IndexUiState(
    val isFetching: Boolean = false,
    val passwordGroups: List<PasswordGroupIndexUiState> = listOf(),
)
