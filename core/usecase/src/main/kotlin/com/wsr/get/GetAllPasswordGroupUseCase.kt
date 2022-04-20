package com.wsr.get

import com.wsr.PasswordGroupUseCaseModel
import com.wsr.maybe.Maybe

interface GetAllPasswordGroupUseCase {
    suspend fun get(userId: String): Maybe<List<PasswordGroupUseCaseModel>, GetAllPasswordGroupUseCaseException>
}

sealed class GetAllPasswordGroupUseCaseException : Throwable() {
    class NoSuchUserException(override val message: String) : GetAllPasswordGroupUseCaseException()
}
