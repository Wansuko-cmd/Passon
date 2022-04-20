package com.wsr.auth

import com.wsr.maybe.Maybe

interface SignUpUseCase {
    suspend fun signUp(
        displayName: String,
        databasePath: String,
        loginPassword: String,
    ): Maybe<Unit, SignUpUseCaseException>
}

sealed class SignUpUseCaseException : Throwable()
