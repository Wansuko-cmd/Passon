package com.wsr.auth

import com.wsr.maybe.Maybe

interface ResetUseCase {
    suspend fun reset(email: String, currentPassword: String, newPassword: String): Maybe<Unit, ResetUseCaseException>
}
