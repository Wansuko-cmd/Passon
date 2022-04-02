package com.wsr.queryservice

import com.wsr.PasswordGroupUseCaseModel
import com.wsr.maybe.Maybe
import com.wsr.user.Email

interface PasswordGroupQueryService {
    suspend fun getAll(email: Email): Maybe<List<PasswordGroupUseCaseModel>, PasswordGroupQueryServiceException>
}

sealed class PasswordGroupQueryServiceException : Throwable() {
    class DatabaseError(override val message: String) : PasswordGroupQueryServiceException()
}
