package com.wsr.sync

import com.wsr.PasswordItemUseCaseModel
import com.wsr.state.State

interface SyncPasswordPairUseCase {
    suspend fun sync(
        passwordGroupId: String,
        title: String,
        remark: String,
        passwordItems: List<PasswordItemUseCaseModel>,
    ): State<Unit, SyncPasswordPairUseCaseException>
}
