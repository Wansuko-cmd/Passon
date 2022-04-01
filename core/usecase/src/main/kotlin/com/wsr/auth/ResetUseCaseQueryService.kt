package com.wsr.auth

import com.wsr.user.Email
import com.wsr.user.LoginPassword

interface ResetUseCaseQueryService {
    suspend fun getPassword(email: Email): LoginPassword.HashedLoginPassword
}

sealed class ResetUseCaseQueryServiceException : Throwable() {
    class NoSuchUserException(override val message: String) : LoginUseCaseQueryServiceException()
    class DatabaseError(override val message: String) : LoginUseCaseQueryServiceException()
}
