package com.wsr.auth

sealed class LoginUseCaseException : Throwable() {
    data class AuthenticationFailedException(override val message: String) : LoginUseCaseException()
    data class NoSuchUserException(override val message: String) : LoginUseCaseException()
    data class SystemError(
        override val message: String,
        override val cause: Throwable,
    ) : LoginUseCaseException()
}
