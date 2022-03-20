package com.wsr.edit

data class EditRefreshEvent(
    val passwordGroup: PasswordGroupEditUiState? = null,
    val passwordItems: List<PasswordItemEditUiState>? = null,
)
