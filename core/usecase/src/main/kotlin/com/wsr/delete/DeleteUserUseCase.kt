package com.wsr.delete

import com.wsr.maybe.Maybe

interface DeleteUserUseCase {
    suspend fun delete(id: String): Maybe<Unit, DeleteUserUseCaseException>
}

sealed class DeleteUserUseCaseException : Throwable() {
    data class NoSuchPasswordGroupException(override val message: String) : DeleteUserUseCaseException()
    data class SystemError(
        override val message: String,
        override val cause: Throwable,
    ) : DeleteUserUseCaseException()
}
