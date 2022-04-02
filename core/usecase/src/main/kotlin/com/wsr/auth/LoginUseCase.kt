package com.wsr.auth

import com.wsr.maybe.Maybe

interface LoginUseCase {
    suspend fun shouldPass(userId: String, password: String): Maybe<Unit, LoginUseCaseException>
}

sealed class LoginUseCaseException : Throwable() {
    data class AuthenticationException(override val message: String) : LoginUseCaseException()
    data class NoSuchUserException(override val message: String) : LoginUseCaseException()
    data class SystemError(
        override val message: String,
        override val cause: Throwable,
    ) : LoginUseCaseException()
}
