package com.wsr.passworditem.upsert

import com.wsr.exceptions.UpsertDataFailedException
import com.wsr.passworditem.PasswordItemUseCaseModel
import com.wsr.state.State

interface UpsertPasswordItemUseCase {
    suspend fun upsert(
        id: String,
        passwordGroupId: String,
        name: String,
        password: String,
    ): State<PasswordItemUseCaseModel, UpsertDataFailedException>
}
