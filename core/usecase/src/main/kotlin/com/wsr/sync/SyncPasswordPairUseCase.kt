package com.wsr.sync

import com.wsr.PasswordItemUseCaseModel
import com.wsr.maybe.Maybe

interface SyncPasswordPairUseCase {
    suspend fun sync(
        passwordGroupId: String,
        title: String,
        remark: String,
        passwordItems: List<PasswordItemUseCaseModel>,
    ): Maybe<Unit, SyncPasswordPairUseCaseException>
}

sealed class SyncPasswordPairUseCaseException : Throwable() {
    data class NoSuchPasswordGroupException(override val message: String) : SyncPasswordPairUseCaseException()
    data class SystemError(
        override val message: String,
        override val cause: Throwable,
    ) : SyncPasswordPairUseCaseException()
}
