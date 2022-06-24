package com.wsr.settings

import com.wsr.UserUseCaseModel

data class DisplayNameSettingsUiState(
    val id: String,
    val displayName: String,
)

fun UserUseCaseModel.toDisplayNameSettingsUiState() =
    DisplayNameSettingsUiState(id = this.id, displayName = this.displayName)

data class ErrorSettingsUiState(
    val message: String,
)
