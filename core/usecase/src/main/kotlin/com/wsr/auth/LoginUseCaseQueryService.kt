package com.wsr.auth

import com.wsr.user.Email
import com.wsr.user.LoginPassword

interface LoginUseCaseQueryService {
    suspend fun getPassword(email: Email): LoginPassword.HashedLoginPassword
}

sealed class LoginUseCaseQueryServiceException : Throwable() {
    class NoSuchUserException(override val message: String) : LoginUseCaseQueryServiceException()
    class DatabaseError(override val message: String) : LoginUseCaseQueryServiceException()
}