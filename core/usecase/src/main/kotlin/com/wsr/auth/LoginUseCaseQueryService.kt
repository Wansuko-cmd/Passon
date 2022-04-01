package com.wsr.auth

import com.wsr.maybe.Maybe
import com.wsr.user.Email
import com.wsr.user.LoginPassword

interface LoginUseCaseQueryService {
    suspend fun getPassword(email: Email): Maybe<LoginPassword.HashedLoginPassword, LoginUseCaseQueryServiceException>
}

sealed class LoginUseCaseQueryServiceException : Throwable() {
    class NoSuchUserException(override val message: String) : LoginUseCaseQueryServiceException()
    class DatabaseError(override val message: String) : LoginUseCaseQueryServiceException()
}
