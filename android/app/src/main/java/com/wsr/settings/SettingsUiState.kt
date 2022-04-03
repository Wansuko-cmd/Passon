package com.wsr.settings

import com.wsr.UserUseCaseModel
import com.wsr.utils.State

data class DisplayNameSettingsUiState(
    val id: String,
    val displayName: String,
)

fun UserUseCaseModel.toDisplayNameSettingsUiState() =
    DisplayNameSettingsUiState(id = this.id, displayName = this.displayName)

data class ErrorSettingsUiState(
    val message: String,
)

data class SettingsUiState(
    val displayName: State<DisplayNameSettingsUiState, ErrorSettingsUiState> = State.Loading,
)