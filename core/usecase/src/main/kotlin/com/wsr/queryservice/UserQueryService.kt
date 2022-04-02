package com.wsr.queryservice

import com.wsr.maybe.Maybe
import com.wsr.user.User
import com.wsr.user.UserId

interface UserQueryService {
    suspend fun get(email: UserId): Maybe<User, UserQueryServiceException>
}

sealed class UserQueryServiceException : Throwable() {
    class NoSuchUserException(override val message: String) : UserQueryServiceException()
    class DatabaseError(override val message: String) : UserQueryServiceException()
}
