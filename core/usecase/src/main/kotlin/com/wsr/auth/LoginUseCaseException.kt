package com.wsr.auth

import com.wsr.create.CreatePasswordGroupUseCaseException

sealed class LoginUseCaseException : Throwable() {
    class AuthenticationFailedException(override val message: String) : LoginUseCaseException()
    class NoSuchUserException(override val message: String) : LoginUseCaseException()
    class SystemError(
        override val message: String,
        override val cause: Throwable,
    ) : LoginUseCaseException()
}
