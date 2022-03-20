package com.wsr.passwordpair.upsert

import com.wsr.exceptions.UpsertDataFailedException
import com.wsr.passwordpair.PasswordPairUseCaseModel
import com.wsr.state.State

interface UpsertPasswordPairUseCase {
    suspend fun upsert(
        id: String,
        passwordGroupId: String,
        name: String,
        password: String,
    ): State<PasswordPairUseCaseModel, UpsertDataFailedException>
}
