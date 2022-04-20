package com.wsr.queryservice

import com.wsr.maybe.Maybe
import com.wsr.passwordgroup.PasswordGroup
import com.wsr.user.UserId

interface PasswordGroupQueryService {
    suspend fun getAll(userId: UserId): Maybe<List<PasswordGroup>, PasswordGroupQueryServiceException>
}

sealed class PasswordGroupQueryServiceException : Throwable() {
    data class SystemError(override val message: String) : PasswordGroupQueryServiceException()
}
