package com.wsr.queryservice

import com.wsr.maybe.Maybe
import com.wsr.user.User
import com.wsr.user.UserId

interface UserQueryService {
    suspend fun get(userId: UserId): Maybe<User, UserQueryServiceException>
    suspend fun getAll(): Maybe<List<User>, UserQueryServiceException>
}

sealed class UserQueryServiceException : Throwable() {
    class NoSuchUserException(override val message: String) : UserQueryServiceException()
    class SystemError(override val message: String) : UserQueryServiceException()
}
