package com.wsr.password

import com.wsr.ext.UniqueId

data class PasswordUseCaseModel(
    val id: String,
    val passwordGroupId: String,
    val name: String,
    val password: String,
)

fun Password.toUseCaseModel() = PasswordUseCaseModel(
    this.id.value,
    this.passwordGroupId.value,
    this.name,
    this.password,
)

fun PasswordUseCaseModel.toPassword() = Password(
    UniqueId(id),
    UniqueId((passwordGroupId)),
    name,
    password,
)