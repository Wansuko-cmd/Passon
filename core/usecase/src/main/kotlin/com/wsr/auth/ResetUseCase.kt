package com.wsr.auth

import com.wsr.state.State

interface ResetUseCase {
    suspend fun reset(email: String, currentPassword: String, newPassword: String): State<Unit, ResetUseCaseException>
}
