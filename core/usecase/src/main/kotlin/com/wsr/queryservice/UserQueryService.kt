package com.wsr.queryservice

import com.wsr.maybe.Maybe
import com.wsr.user.Email
import com.wsr.user.User

interface UserQueryService {
    suspend fun get(email: Email): Maybe<User, UserQueryServiceException>
}

sealed class UserQueryServiceException : Throwable() {
    class NoSuchUserException(override val message: String) : UserQueryServiceException()
    class DatabaseError(override val message: String) : UserQueryServiceException()
}
