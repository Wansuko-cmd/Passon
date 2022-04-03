package com.wsr.update

import com.wsr.maybe.Maybe

interface UpdateUserUseCase {
    suspend fun update(userId: String, displayName: String): Maybe<Unit, UpdateUserUseCaseException>
}

sealed class UpdateUserUseCaseException : Throwable() {
    data class NoSuchUserException(override val message: String) : UpdateUserUseCaseException()
    data class SystemError(
        override val message: String,
        override val cause: Throwable,
    ) : UpdateUserUseCaseException()
}
