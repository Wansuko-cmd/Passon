package com.wsr.show

import com.wsr.PasswordGroupUseCaseModel
import com.wsr.PasswordItemUseCaseModel
import com.wsr.utils.State

data class PasswordItemShowUiState(
    val id: String,
    val name: String,
    val password: String,
    val shouldShowPassword: Boolean,
) {
    companion object {
        fun PasswordItemUseCaseModel.toShowUiModel() =
            PasswordItemShowUiState(id = id, name = name, password = password, shouldShowPassword = false)
    }
}

data class PasswordGroupShowUiState(
    val id: String,
    val title: String,
    val remark: String,
) {
    companion object {
        fun PasswordGroupUseCaseModel.toShowUiModel() =
            PasswordGroupShowUiState(id = id, title = title, remark = remark)
    }
}

data class ErrorShowUiState(val message: String)

data class ShowUiState(
    val passwordGroup: State<PasswordGroupShowUiState, ErrorShowUiState> = State.Loading,
    val passwordItems: State<List<PasswordItemShowUiState>, ErrorShowUiState> = State.Loading,
) {
    fun copyWithPasswordGroup(passwordGroup: State<PasswordGroupShowUiState, ErrorShowUiState>) =
        this.copy(passwordGroup = passwordGroup)

    fun copyWithPasswordItems(passwordItems: State<List<PasswordItemShowUiState>, ErrorShowUiState>) =
        this.copy(passwordItems = passwordItems)
}
