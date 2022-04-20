package com.wsr.get

import com.wsr.PasswordPairUseCaseModel
import com.wsr.maybe.Maybe

interface GetPasswordPairUseCase {
    suspend fun get(passwordGroupId: String): Maybe<PasswordPairUseCaseModel, GetUserUseCaseException>
}

sealed class GetPasswordPairUseCaseException : Throwable() {
    data class NoSuchPasswordGroupException(override val message: String) : GetUserUseCaseException()
}
