package com.wsr.index

import com.wsr.passwordgroup.PasswordGroupUseCaseModel

data class PasswordGroupIndexUIModel(
    val id: String,
    val title: String,
)

fun PasswordGroupUseCaseModel.toIndexUIModel() =
    PasswordGroupIndexUIModel(this.id, this.title)
