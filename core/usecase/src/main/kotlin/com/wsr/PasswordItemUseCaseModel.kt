package com.wsr

import com.wsr.passworditem.PasswordItem

data class PasswordItemUseCaseModel(
    val id: String,
    val passwordGroupId: String,
    val name: String,
    val password: String,
)

fun PasswordItem.toUseCaseModel() = PasswordItemUseCaseModel(
    id = this.id.value,
    passwordGroupId = this.passwordGroupId.value,
    name = this.name.value,
    password = this.password.value,
)
