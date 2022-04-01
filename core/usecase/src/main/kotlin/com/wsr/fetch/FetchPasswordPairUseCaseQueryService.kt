package com.wsr.fetch

import com.wsr.PasswordPairUseCaseModel
import com.wsr.passwordgroup.PasswordGroupId

interface FetchPasswordPairUseCaseQueryService {
    suspend fun getPasswordPair(passwordGroupId: PasswordGroupId): PasswordPairUseCaseModel
}

sealed class FetchPasswordPairUseCaseQueryServiceException : Throwable() {
    class NoSuchUserException(override val message: String) : FetchPasswordPairUseCaseQueryServiceException()
    class DatabaseError(override val message: String) : FetchPasswordPairUseCaseQueryServiceException()
}
