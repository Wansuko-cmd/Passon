package com.wsr.password

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
