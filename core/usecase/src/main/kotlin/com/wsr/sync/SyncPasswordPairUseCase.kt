package com.wsr.sync

import com.wsr.PasswordItemUseCaseModel

interface SyncPasswordPairUseCase {
    suspend fun sync(
        passwordGroupId: String,
        title: String,
        remark: String,
        passwordItems: List<PasswordItemUseCaseModel>,
    )
}
