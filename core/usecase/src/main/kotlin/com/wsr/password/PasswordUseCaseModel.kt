package com.wsr.password

data class PasswordUseCaseModel(
    val id: String,
    val passwordGroupId: String,
    val name: String,
    val password: String,
)

fun Password.toUseCaseModel() = PasswordUseCaseModel(
    id = this.id.value,
    passwordGroupId = this.passwordGroupId.value,
    name = this.name,
    password = this.password,
)
