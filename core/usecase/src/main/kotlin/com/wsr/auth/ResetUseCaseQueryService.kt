package com.wsr.auth

import com.wsr.maybe.Maybe
import com.wsr.user.Email
import com.wsr.user.LoginPassword

interface ResetUseCaseQueryService {
    suspend fun getPassword(email: Email): Maybe<LoginPassword.HashedLoginPassword, ResetUseCaseQueryServiceException>
}

sealed class ResetUseCaseQueryServiceException : Throwable() {
    class NoSuchUserException(override val message: String) : ResetUseCaseQueryServiceException()
    class DatabaseError(override val message: String) : ResetUseCaseQueryServiceException()
}
