package com.wsr.auth

import com.wsr.user.Email
import com.wsr.user.LoginPassword

interface LoginUseCaseQueryService {
    suspend fun getPassword(email: Email): LoginPassword.HashedLoginPassword
}
