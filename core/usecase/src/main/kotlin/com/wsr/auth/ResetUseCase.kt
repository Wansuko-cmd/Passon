package com.wsr.auth

import com.wsr.maybe.Maybe

interface ResetUseCase {
    suspend fun reset(email: String, currentPassword: String, newPassword: String): Maybe<Unit, ResetUseCaseException>
}

sealed class ResetUseCaseException : Throwable() {
    data class AuthenticationFailedException(override val message: String) : ResetUseCaseException()
    data class NoSuchUserException(override val message: String) : ResetUseCaseException()
    data class SystemError(
        override val message: String,
        override val cause: Throwable,
    ) : ResetUseCaseException()
}
