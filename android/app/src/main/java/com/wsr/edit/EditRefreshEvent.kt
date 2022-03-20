package com.wsr.edit

data class EditRefreshEvent(
    val passwordGroup: PasswordGroupEditUiState? = null,
    val passwordPairs: List<PasswordPairEditUiState>? = null,
)
