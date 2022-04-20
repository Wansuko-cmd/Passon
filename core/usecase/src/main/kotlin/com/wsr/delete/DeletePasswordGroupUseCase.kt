package com.wsr.delete

import com.wsr.maybe.Maybe

interface DeletePasswordGroupUseCase {
    suspend fun delete(id: String): Maybe<Unit, DeletePasswordGroupUseCaseException>
}

sealed class DeletePasswordGroupUseCaseException : Throwable() {
    data class NoSuchPasswordGroupException(override val message: String) : DeletePasswordGroupUseCaseException()
}
