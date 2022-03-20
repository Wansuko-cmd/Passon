package com.wsr.passwordpair.create

import com.wsr.passwordpair.PasswordPairUseCaseModel

interface CreatePasswordPairUseCase {
    fun createPasswordInstance(passwordGroupId: String): PasswordPairUseCaseModel
}
