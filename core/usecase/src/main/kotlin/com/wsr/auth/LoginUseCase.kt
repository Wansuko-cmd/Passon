package com.wsr.auth

import com.wsr.state.State

interface LoginUseCase {
    suspend fun shouldPass(email: String, password: String): State<Boolean, LoginUseCaseException>
}
