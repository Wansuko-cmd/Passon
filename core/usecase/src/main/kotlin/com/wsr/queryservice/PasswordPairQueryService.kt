package com.wsr.queryservice

import com.wsr.PasswordPairUseCaseModel
import com.wsr.maybe.Maybe
import com.wsr.passwordgroup.PasswordGroupId

interface PasswordPairQueryService {
    suspend fun get(passwordGroupId: PasswordGroupId): Maybe<PasswordPairUseCaseModel, PasswordGroupQueryServiceException>
}

sealed class PasswordPairQueryServiceException : Throwable() {
    class NoSuchUserException(override val message: String) : PasswordPairQueryServiceException()
    class DatabaseError(override val message: String) : PasswordPairQueryServiceException()
}
