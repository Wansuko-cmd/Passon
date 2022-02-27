package com.wsr.password.upsert

import com.wsr.password.PasswordUseCaseModel
import com.wsr.state.State

interface UpsertPasswordUseCase {
    suspend fun upsert(
        id: String,
        passwordGroupId: String,
        name: String,
        password: String,
    ): State<PasswordUseCaseModel, Throwable>
}