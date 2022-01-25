package com.wsr.index

import com.wsr.passwordgroup.PasswordGroupUseCaseModel

data class PasswordGroupIndexUIState(
    val id: String,
    val title: String,
)

fun PasswordGroupUseCaseModel.toIndexUIState() =
    PasswordGroupIndexUIState(this.id, this.title)

data class IndexUIState(
    val isFetching: Boolean = false,
    val passwordGroups: List<PasswordGroupIndexUIState> = listOf(),
)
