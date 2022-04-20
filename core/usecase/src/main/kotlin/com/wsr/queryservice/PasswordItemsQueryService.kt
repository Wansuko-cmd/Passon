package com.wsr.queryservice

import com.wsr.maybe.Maybe
import com.wsr.passwordgroup.PasswordGroupId
import com.wsr.passworditem.PasswordItem

interface PasswordItemsQueryService {
    suspend fun getAll(passwordGroupId: PasswordGroupId): Maybe<List<PasswordItem>, PasswordItemsQueryServiceException>
}

sealed class PasswordItemsQueryServiceException : Throwable() {
    class NoSuchPasswordGroupException(override val message: String) : PasswordItemsQueryServiceException()
    class SystemError(override val message: String, override val cause: Throwable) : PasswordItemsQueryServiceException()
}
