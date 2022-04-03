package com.wsr.get

import com.wsr.UserUseCaseModel
import com.wsr.maybe.Maybe

interface GetUserUseCase {
    suspend fun get(userId: String): Maybe<UserUseCaseModel, GetUserUseCaseException>
}

sealed class GetUserUseCaseException : Throwable() {
    data class NoSuchUserException(override val message: String) : GetUserUseCaseException()
    data class SystemError(
        override val message: String,
        override val cause: Throwable,
    ) : GetUserUseCaseException()
}
