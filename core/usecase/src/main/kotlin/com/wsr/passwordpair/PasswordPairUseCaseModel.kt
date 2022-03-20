package com.wsr.passwordpair

data class PasswordPairUseCaseModel(
    val id: String,
    val passwordGroupId: String,
    val name: String,
    val password: String,
)

fun PasswordPair.toUseCaseModel() = PasswordPairUseCaseModel(
    id = this.id.value,
    passwordGroupId = this.passwordGroupId.value,
    name = this.name.value,
    password = this.password.value,
)
