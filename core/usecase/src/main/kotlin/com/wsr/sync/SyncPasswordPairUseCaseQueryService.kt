package com.wsr.sync

import com.wsr.maybe.Maybe
import com.wsr.passwordgroup.PasswordGroupId
import com.wsr.passworditem.PasswordItemId

interface SyncPasswordPairUseCaseQueryService {
    suspend fun getAllPasswordItemId(passwordGroupId: PasswordGroupId): Maybe<List<PasswordItemId>, SyncPasswordPairUseCaseException>
}

sealed class SyncPasswordPairUseCaseQueryServiceException : Throwable() {
    class NoSuchUserException(override val message: String) : SyncPasswordPairUseCaseQueryServiceException()
    class DatabaseError(override val message: String) : SyncPasswordPairUseCaseQueryServiceException()
}
