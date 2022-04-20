package com.wsr.queryservice

import com.wsr.maybe.Maybe
import com.wsr.user.User

interface UsersQueryService {
    suspend fun getAll(): Maybe<List<User>, UsersQueryServiceException>
}

sealed class UsersQueryServiceException : Throwable() {
    class SystemError(override val message: String, override val cause: Throwable) : UsersQueryServiceException()
}
