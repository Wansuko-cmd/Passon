package com.wsr.fetch

import com.wsr.PasswordGroupUseCaseModel
import com.wsr.user.Email

interface FetchAllPasswordGroupUseCaseQueryService {
    suspend fun getAllPasswordGroup(email: Email): List<PasswordGroupUseCaseModel>
}

sealed class FetchAllPasswordGroupUseCaseQueryServiceException : Throwable() {
    class DatabaseError(override val message: String) : FetchAllPasswordGroupUseCaseException()
}
