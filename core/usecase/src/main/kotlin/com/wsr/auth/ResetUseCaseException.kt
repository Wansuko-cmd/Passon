package com.wsr.auth

sealed class ResetUseCaseException : Throwable() {
    class AuthenticationFailedException(override val message: String) : ResetUseCaseException()
    class NoSuchUserException(override val message: String) : ResetUseCaseException()
    class SystemError(
        override val message: String,
        override val cause: Throwable,
    ) : ResetUseCaseException()
}
