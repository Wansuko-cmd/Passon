package com.wsr.edit

data class EditRefreshEvent(
    val passwordGroup: PasswordGroupEditUiState? = null,
    val passwords: List<PasswordEditUiState>? = null,
)