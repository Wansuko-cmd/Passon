package com.wsr.fetch

import com.wsr.PasswordPairUseCaseModel
import com.wsr.passwordgroup.PasswordGroupId
import com.wsr.state.State

interface FetchPasswordPairUseCaseQueryService {
    suspend fun getPasswordPair(passwordGroupId: PasswordGroupId): State<PasswordPairUseCaseModel, FetchPasswordPairUseCaseQueryServiceException>
}

sealed class FetchPasswordPairUseCaseQueryServiceException : Throwable() {
    class NoSuchUserException(override val message: String) : FetchPasswordPairUseCaseQueryServiceException()
    class DatabaseError(override val message: String) : FetchPasswordPairUseCaseQueryServiceException()
}
