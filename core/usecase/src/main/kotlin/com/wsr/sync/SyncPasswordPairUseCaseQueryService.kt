package com.wsr.sync

import com.wsr.passwordgroup.PasswordGroupId
import com.wsr.passworditem.PasswordItemId
import com.wsr.state.State

interface SyncPasswordPairUseCaseQueryService {
    suspend fun getAllPasswordItemId(passwordGroupId: PasswordGroupId): State<List<PasswordItemId>, SyncPasswordPairUseCaseException>
}

sealed class SyncPasswordPairUseCaseQueryServiceException : Throwable() {
    class NoSuchUserException(override val message: String) : SyncPasswordPairUseCaseQueryServiceException()
    class DatabaseError(override val message: String) : SyncPasswordPairUseCaseQueryServiceException()
}
