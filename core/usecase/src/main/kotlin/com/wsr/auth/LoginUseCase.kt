package com.wsr.auth

import com.wsr.maybe.Maybe

interface LoginUseCase {
    suspend fun shouldPass(email: String, password: String): Maybe<Boolean, LoginUseCaseException>
}
