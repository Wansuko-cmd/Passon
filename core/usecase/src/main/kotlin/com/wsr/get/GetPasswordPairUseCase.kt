package com.wsr.get

import com.wsr.PasswordPairUseCaseModel
import com.wsr.maybe.Maybe

interface GetPasswordPairUseCase {
    suspend fun get(passwordGroupId: String): Maybe<PasswordPairUseCaseModel, GetPasswordPairUseCaseException>
}
