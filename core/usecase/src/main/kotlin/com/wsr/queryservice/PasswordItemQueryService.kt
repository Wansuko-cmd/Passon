package com.wsr.queryservice

import com.wsr.maybe.Maybe
import com.wsr.passwordgroup.PasswordGroupId
import com.wsr.passworditem.PasswordItem

interface PasswordItemQueryService {
    suspend fun getAll(passwordGroupId: PasswordGroupId): Maybe<List<PasswordItem>, PasswordItemQueryServiceException>
}

sealed class PasswordItemQueryServiceException : Throwable() {
    class NoSuchUserException(override val message: String) : PasswordItemQueryServiceException()
    class DatabaseError(override val message: String) : PasswordItemQueryServiceException()
}
