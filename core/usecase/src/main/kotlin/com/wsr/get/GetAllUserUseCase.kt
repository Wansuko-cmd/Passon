package com.wsr.get

import com.wsr.UserUseCaseModel
import com.wsr.maybe.Maybe

interface GetAllUserUseCase {
    suspend fun getAll(): Maybe<List<UserUseCaseModel>, GetAllUserUseCaseException>
}

sealed class GetAllUserUseCaseException : Throwable() {
    data class SystemError(
        override val message: String,
        override val cause: Throwable,
    ) : GetAllUserUseCaseException()
}
