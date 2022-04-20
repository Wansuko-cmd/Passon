package com.wsr.queryservice

import com.wsr.PasswordPairUseCaseModel
import com.wsr.maybe.Maybe
import com.wsr.passwordgroup.PasswordGroupId

interface PasswordPairQueryService {
    suspend fun get(passwordGroupId: PasswordGroupId): Maybe<PasswordPairUseCaseModel, PasswordPairQueryServiceException>
}

sealed class PasswordPairQueryServiceException : Throwable() {
    class NoSuchPasswordGroupException(override val message: String) : PasswordPairQueryServiceException()
    class SystemError(override val message: String, override val cause: Throwable) : PasswordPairQueryServiceException()
}
