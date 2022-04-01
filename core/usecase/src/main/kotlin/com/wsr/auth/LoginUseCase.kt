package com.wsr.auth

import com.wsr.maybe.Maybe

interface LoginUseCase {
    suspend fun shouldPass(email: String, password: String): Maybe<Boolean, LoginUseCaseException>
}

sealed class LoginUseCaseException : Throwable() {
    data class NoSuchUserException(override val message: String) : LoginUseCaseException()
    data class SystemError(
        override val message: String,
        override val cause: Throwable,
    ) : LoginUseCaseException()
}
