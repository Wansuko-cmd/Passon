package com.wsr.auth

import com.wsr.maybe.Maybe

interface ResetUseCase {
    suspend fun reset(userId: String, currentPassword: String, newPassword: String): Maybe<Unit, ResetUseCaseException>
}

sealed class ResetUseCaseException : Throwable() {
    data class AuthenticationException(override val message: String) : ResetUseCaseException()
    data class NoSuchUserException(override val message: String) : ResetUseCaseException()
    data class SystemError(
        override val message: String,
        override val cause: Throwable,
    ) : ResetUseCaseException()
}
