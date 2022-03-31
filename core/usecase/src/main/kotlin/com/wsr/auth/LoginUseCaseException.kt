package com.wsr.auth

sealed class LoginUseCaseException : Throwable() {
    class AuthenticationFailedException(override val message: String) : LoginUseCaseException()
    class NoSuchUserException(override val message: String) : LoginUseCaseException()
}
