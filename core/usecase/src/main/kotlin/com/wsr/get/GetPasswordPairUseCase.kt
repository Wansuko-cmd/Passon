package com.wsr.get

import com.wsr.PasswordPairUseCaseModel
import com.wsr.maybe.Maybe

interface GetPasswordPairUseCase {
    suspend fun get(passwordGroupId: String): Maybe<PasswordPairUseCaseModel, GetPasswordPairUseCaseException>
}

sealed class GetPasswordPairUseCaseException : Throwable() {
    data class NoSuchPasswordGroupException(override val message: String) : GetPasswordPairUseCaseException()
    data class SystemError(
        override val message: String,
        override val cause: Throwable,
    ) : GetPasswordPairUseCaseException()
}
