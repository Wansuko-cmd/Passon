package com.wsr.queryservice

import com.wsr.maybe.Maybe
import com.wsr.passwordgroup.PasswordGroup
import com.wsr.user.UserId

interface PasswordGroupQueryService {
    suspend fun getAll(userId: UserId): Maybe<List<PasswordGroup>, PasswordGroupsQueryServiceException>
}

sealed class PasswordGroupsQueryServiceException : Throwable() {
    data class NoSuchUserException(override val message: String) : PasswordGroupsQueryServiceException()
    data class SystemError(override val message: String, override val cause: Throwable) : PasswordGroupsQueryServiceException()
}
