package com.wsr.auth

sealed class ResetUseCaseException : Throwable() {
    data class AuthenticationFailedException(override val message: String) : ResetUseCaseException()
    data class NoSuchUserException(override val message: String) : ResetUseCaseException()
    data class SystemError(
        override val message: String,
        override val cause: Throwable,
    ) : ResetUseCaseException()
}
