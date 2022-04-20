package com.wsr.delete

import com.wsr.maybe.Maybe

interface DeleteUserUseCase {
    suspend fun delete(id: String): Maybe<Unit, DeleteUserUseCaseException>
}

sealed class DeleteUserUseCaseException : Throwable() {
    data class NoSuchUserUseCaseException(override val message: String) : DeleteUserUseCaseException()
}
