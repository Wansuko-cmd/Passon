package com.wsr

data class PasswordPairUseCaseModel(
    val passwordGroup: PasswordGroupUseCaseModel,
    val passwordItems: List<PasswordItemUseCaseModel>,
)
