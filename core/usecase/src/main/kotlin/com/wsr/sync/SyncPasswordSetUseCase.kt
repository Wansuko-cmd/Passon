package com.wsr.sync

import com.wsr.PasswordItemUseCaseModel

interface SyncPasswordSetUseCase {
    suspend fun sync(
        passwordGroupId: String,
        title: String,
        remark: String,
        passwordItems: List<PasswordItemUseCaseModel>,
    )
}
