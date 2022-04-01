package com.wsr.get

import com.wsr.PasswordGroupUseCaseModel
import com.wsr.maybe.Maybe
import com.wsr.user.Email

interface FetchAllPasswordGroupUseCaseQueryService {
    suspend fun getAllPasswordGroup(email: Email): Maybe<List<PasswordGroupUseCaseModel>, FetchAllPasswordGroupUseCaseQueryServiceException>
}

sealed class FetchAllPasswordGroupUseCaseQueryServiceException : Throwable() {
    class DatabaseError(override val message: String) : FetchAllPasswordGroupUseCaseQueryServiceException()
}
