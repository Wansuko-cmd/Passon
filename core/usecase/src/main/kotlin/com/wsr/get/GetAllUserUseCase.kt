package com.wsr.get

import com.wsr.maybe.Maybe
import com.wsr.user.User

interface GetAllUserUseCase {
    suspend fun getAll(): Maybe<List<User>, GetAllUserUseCaseException>
}

sealed class GetAllUserUseCaseException : Throwable() {
    data class SystemError(
        override val message: String,
        override val cause: Throwable,
    ) : GetAllUserUseCaseException()
}
