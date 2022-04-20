package com.wsr.auth

import com.wsr.maybe.Maybe

interface ResetLoginPasswordUseCase {
    suspend fun reset(userId: String, newPassword: String): Maybe<Unit, ResetLoginPasswordUseCaseException>
}

sealed class ResetLoginPasswordUseCaseException : Throwable() {
    data class NoSuchUserException(override val message: String) : ResetLoginPasswordUseCaseException()
}
